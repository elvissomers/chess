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
import com.practice.project.chess.service.logic.MoveService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.project.chess.service.logic.AllUtil.getOpponentPlayer;
import static com.practice.project.chess.service.logic.game.util.MoveUtil.updateSpecialMove;
import static com.practice.project.chess.service.logic.game.GameService.getPieceForTeamAndPosition;
import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.*;
import static com.practice.project.chess.service.logic.game.util.PlayerUtil.getPlayerKing;

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
        Player playerInTurn = playerInTurn(game);
        updateGameAfterMove(playerInTurn, madeMove.getMove());
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

    private void getMoveDetails(Move move, long gameId) {
        CastleType castleType = getTypeIfCastled(move);
        PieceType promotionPiece = getNewPieceIfPromoted(move);
        updateSpecialMove(move, castleType, promotionPiece);
        moveService.setTakenPieceIfEnPassant(move, gameId);
    }

    private void updateGameAfterMove(Player player, Move move) {
        if (move.getTakenPiece() != null)
            playerService.removePiece(player, move.getTakenPiece());
        else if (move.getPromotedTo() != null)
            playerService.promotePawnTo(player, move);
        else
            playerService.updateDaoListFromMovedPiece(player, pieceService.getPieceWithNewPosition(move));
    }

    private void processMove(Game game) {
        setOtherDraws(game);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
    }

    private void setCheckOrStaleMate(Game game) {
        Player playerInTurn = playerInTurn(game);
        setPlayerKingInCheck(game, playerInTurn, getOpponentPlayer(game, playerInTurn));
        if (!canPlayerMove(playerInTurn, game)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
    }

    private void setPlayerKingInCheck(Game game, Player player, Player attackingPlayer) {
        King playerKing = getPlayerKing(player);
        playerService.setAllAttackedAndMovableSquaresForPlayer(game, attackingPlayer);
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

    private boolean canPlayerMove(Player player, Game game) {
        legalMoveService.setAllLegalMovableSquaresForPlayer(player, game);
        return !playerService.getAllMovableSquaresForPlayer(player).isEmpty();
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
