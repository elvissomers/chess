package com.practice.project.chess.service.gameservice;

import com.practice.project.chess.controller.dto.GameDto;
import com.practice.project.chess.controller.dto.MovePieceDto;
import com.practice.project.chess.controller.dto.mapper.GameDtoMapper;
import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.PlayerMove;
import com.practice.project.chess.repository.entity.pieces.King;
import com.practice.project.chess.repository.entity.pieces.Pawn;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.exception.InvalidMoveException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.gameservice.pieces.KingService;
import com.practice.project.chess.service.gameservice.pieces.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GameService {

    private final PieceService pieceService;
    private final PlayerService playerService;
    private final KingService kingService;
    private final MoveService moveService;

    private final GameRepository gameRepository;

    private final GameDtoMapper gameDtoMapper;

    public Game getGame(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
    }

    public GameDto getGameDto(long id) {
        return gameRepository.findById(id)
                .map(gameDtoMapper::gameToGameDto)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
    }

    public Player getPlayerForGameAndTeam(long gameId, Team team) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }

    public Player getOpponentPlayerForGameAndTeam(long gameId, Team team) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
        return (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public GameDto getNewGame() {
        Game game = new Game();
        Player whitePlayer = Player.builder().game(game).team(Team.WHITE).build();
        Player blackPlayer = Player.builder().game(game).team(Team.BLACK).build();

        processNewPlayers(game, whitePlayer, blackPlayer);
        return gameDtoMapper.gameToGameDto(gameRepository.save(game));
    }

    private void processNewPlayers(Game game, Player whitePlayer, Player blackPlayer) {
        playerService.setStartPiecesForPlayer(whitePlayer);
        playerService.setStartPiecesForPlayer(blackPlayer);

        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setGameState(GameState.WHITE_TURN);
    }

    public void makeMoveFromDto(MovePieceDto dto) {
        Game game = getGame(dto.getGameId());
        Piece piece = pieceService.getPieceForGameAndPosition(dto.getXFrom(), dto.getYFrom(), dto.getGameId());
        Coordinate destination = new Coordinate(dto.getXTo(), dto.getYTo());

        makeMove(game, piece, destination);
    }

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
        Piece takenPiece = pieceService.getPieceForGameAndPosition(destination.getXPos(),
                destination.getYPos(), game.getId());
        Move newMove = moveService.getOrCreateMove(piece, destination, takenPiece);
        getMoveDetails(newMove, game.getId());
        return moveService.saveMoveForPlayer(newMove, playerInTurn(game));
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
        return (piece.getPlayer().getTeam() == Team.WHITE) ? 7 : 0;
    }

    private void checkIfPieceInTurn(Game game, Piece piece) {
        if ((game.getGameState() == GameState.WHITE_TURN && piece.getPlayer().getTeam() == Team.WHITE)
                || (game.getGameState() == GameState.BLACK_TURN && piece.getPlayer().getTeam() == Team.BLACK)) {
            return;
        }
        throw new InvalidMoveException("Not players turn!");
    }

    private void updateGameAfterMove(Move move) {
        if (move.getTakenPiece() != null)
            pieceService.removePiece(move.getTakenPiece());

        if (move.getPromotedTo() != null)
             pieceService.promotePawnTo(move);
        else
            pieceService.updatePosition(move);
    }

    private void processMove(Game game) {
        setOtherDraws(game);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
        // TODO check for check, for the "+" in notation (and maybe some other visual effect)
    }

    private void updatePlayerTurn(Game game) {
        GameState nextPlayerTurn = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN
                : GameState.WHITE_TURN;
        game.setGameState(nextPlayerTurn);
    }

    private void setCheckOrStaleMate(Game game) {
        Player playerInTurn = playerInTurn(game);
        if (!canPlayerMove(playerInTurn)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
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
        King playerKing = playerService.getPlayerKing(player);
        kingService.setup(playerKing, player.getGame().getId());
        return kingService.isInCheck();
    }

    private boolean canPlayerMove(Player player) {
        playerService.setAllAttackedAndMovableSquaresForPlayer(player);
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

    private boolean goesBackAndForth(List<Move> moves) {
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
