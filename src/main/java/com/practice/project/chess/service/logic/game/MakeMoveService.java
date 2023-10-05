package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import com.practice.project.chess.service.model.pieces.King;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.exception.InvalidMoveException;
import com.practice.project.chess.service.logic.MoveService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.project.chess.service.logic.AllUtil.getOpponentPlayer;
import static com.practice.project.chess.service.logic.game.GameService.getPieceForTeamAndPosition;

@AllArgsConstructor
@Service
public class MakeMoveService {

    private final PlayerService playerService;
    private final PieceService pieceService;
    private final MoveService moveService;
    private final LegalMoveService legalMoveService;

    public void makeMove(Game game, Piece piece, Coordinate destination) {
        checkMove(game, piece, destination);
        PlayerMove madeMove = updateMoveHistory(game, piece, destination);
        updateGameAfterMove(madeMove.getMove());
        processMove(game);
    }

    private void checkMove(Game game, Piece piece, Coordinate destination) {
        pieceService.checkMoveLegality(piece, destination);
        checkIfPieceInTurn(game, piece);
    }

    private PlayerMove updateMoveHistory(Game game, Piece piece, Coordinate destination) {
        Team opponentTeam = getOtherTeam(piece.getTeam());
        Piece takenPiece = getPieceForTeamAndPosition(game, opponentTeam, destination);
        Move newMove = moveService.getOrCreateMove(piece, destination, takenPiece);
        getMoveDetails(newMove, game.getId());
        return playerService.saveMoveForPlayer(newMove, playerInTurn(game));
    }

    private static Team getOtherTeam(Team team) {
        return (team == Team.WHITE) ? Team.BLACK : Team.WHITE;
    }

    private void getMoveDetails(Move move, long gameId) {
        CastleType castleType = getTypeIfCastled(move);
        PieceType promotionPiece = getNewPieceIfPromoted(move);
        moveService.updateSpecialMove(move, castleType, promotionPiece);
        moveService.setTakenPieceIfEnPassant(move, gameId);
    }

    private CastleType getTypeIfCastled(Move move) {
        if (move.getPiece().getPieceType() == PieceType.KING) {
            if (move.getHorizontalFrom() - move.getHorizontalTo() == 2)
                return CastleType.LONG;
            else if (move.getHorizontalFrom() - move.getHorizontalTo() == -2)
                return CastleType.SHORT;
        }
        return null;
    }

    private PieceType getNewPieceIfPromoted(Move move) {
        // TODO 2) Get the wanted PieceType as input from the used instead of just hardcoding it to queen
        if (move.getPiece().getPieceType() == PieceType.PAWN) {
            if (move.getVerticalTo() == getPromotionRank(move.getPiece()))
                return PieceType.QUEEN;
        }
        return null;
    }

    private int getPromotionRank(Piece piece) {
        return (piece.getTeam() == Team.WHITE) ? 7 : 0;
    }

    private void checkIfPieceInTurn(Game game, Piece piece) {
        if ((game.getGameState() == GameState.WHITE_TURN && piece.getTeam() == Team.WHITE)
                || (game.getGameState() == GameState.BLACK_TURN && piece.getTeam() == Team.BLACK)) {
            return;
        }
        throw new InvalidMoveException("Not players turn!");
    }

    private void updateGameAfterMove(Move move) {
        if (move.getTakenPiece() != null)
            playerService.removePiece(move.getTakenPiece());

        if (move.getPromotedTo() != null)
            playerService.promotePawnTo(move);
        else
            pieceService.updatePosition(move);
    }

    private void processMove(Game game) {
        setOtherDraws(game);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
    }

    private static void updatePlayerTurn(Game game) {
        GameState nextPlayerTurn = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN
                : GameState.WHITE_TURN;
        game.setGameState(nextPlayerTurn);
    }

    private void setCheckOrStaleMate(Game game) {
        Player playerInTurn = playerInTurn(game);
        setPlayerKingInCheck(playerInTurn, getOpponentPlayer(game, playerInTurn));
        if (!canPlayerMove(playerInTurn, game)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
    }

    private void setPlayerKingInCheck(Player player, Player attackingPlayer) {
        King playerKing = playerService.getPlayerKing(player);
        playerService.setAllAttackedAndMovableSquaresForPlayer(attackingPlayer);
        List<Coordinate> attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        playerKing.setInCheck(attackedSquares.contains(new Coordinate(playerKing.getHorizontalPosition(), playerKing.getVerticalPosition())));
    }

    private Player playerInTurn(Game game) {
        if (game.getGameState() == GameState.WHITE_TURN)
            return game.getWhitePlayer();
        else if (game.getGameState() == GameState.BLACK_TURN)
            return game.getBlackPlayer();
        else
            throw new ElementNotFoundException("No Player in turn!");
    }

    private boolean isPlayerInCheck(Player player) {
        return playerService.getPlayerKing(player).isInCheck();
    }

    private boolean canPlayerMove(Player player, Game game) {
        legalMoveService.setAllLegalMovableSquaresForPlayer(player, game);
        return !playerService.getAllMovableSquaresForPlayer(player).isEmpty();
    }

    private GameState opponentWins(Player player) {
        return (player.getTeam() == Team.WHITE) ? GameState.BLACK_WINS : GameState.WHITE_WINS;
    }

    private boolean hasThreeFoldDraw(Game game) {
        // TODO: Use real three fold draw rule
        return (playerHasThreeFoldDraw(game.getWhitePlayer()) && playerHasThreeFoldDraw(game.getBlackPlayer()));
    }

    private boolean playerHasThreeFoldDraw(Player player) {
        int historySize = playerService.getNumberOfMoves(player.getId());
        if (historySize < 6)
            return false;

        List<Move> lastSixMoves = playerService.getLastNMoves(6, player.getId());
        return (goesBackAndForth(lastSixMoves));
    }

    private static boolean goesBackAndForth(List<Move> moves) {
        return (moves.get(0).equals(moves.get(2)) && moves.get(2).equals(moves.get(4)) &&
                moves.get(1).equals(moves.get(3)) && moves.get(3).equals(moves.get(5)));
    }

    private void setOtherDraws(Game game) {
        if (hasFiftyMoveDraw(game) || hasThreeFoldDraw(game))
            game.setGameState(GameState.DRAW);
    }

    private boolean hasFiftyMoveDraw(Game game) {
        return playerHasFiftyMoveDraw(playerInTurn(game));
    }

    private boolean playerHasFiftyMoveDraw(Player player) {
        if (playerService.getNumberOfMoves(player.getId()) < 50)
            return false;

        List<Move> lastFiftyMoves = playerService.getLastNMoves(50, player.getId());
        for (Move move : lastFiftyMoves) {
            if (move.getPiece() instanceof Pawn || move.getTakenPiece() == null) {
                return false;
            }
        }
        return true;
    }
}
