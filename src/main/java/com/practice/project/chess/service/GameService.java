package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.data.dto.mapper.GameDtoMapper;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Move;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.PlayerMove;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.CastleType;
import com.practice.project.chess.enums.GameState;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.exception.InvalidMoveException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.pieces.KingService;
import com.practice.project.chess.service.pieces.PieceService;
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

    public Game getGame(long id) throws ElementNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
    }

    public GameDto getGameDto(long id) throws ElementNotFoundException {
        return gameRepository.findById(id)
                .map(gameDtoMapper::gameToGameDto)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
    }

    public Player getPlayerForGameAndTeam(long gameId, Team team) throws ElementNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }

    public Player getOpponentPlayerForGameAndTeam(long gameId, Team team) throws ElementNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
        return (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public GameDto getNewGame() {
        // TODO: split in create new game and get new game
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

    public void makeMove(long gameId, long pieceId, Coordinate destination)
            throws ElementNotFoundException, InvalidMoveException {
        Game game = getGame(gameId);
        checkMove(game, pieceId, destination);
        PlayerMove madeMove = updateMoveHistory(game, pieceId, destination);
        updatePieceAfterMove(madeMove.getMove());
        processMove(game);
    }

    private void checkMove(Game game, long pieceId, Coordinate destination) throws ElementNotFoundException, InvalidMoveException {
        pieceService.checkMoveLegality(pieceId, destination);
        checkIfPieceInTurn(game, pieceId);
    }

    private PlayerMove updateMoveHistory(Game game, long pieceId, Coordinate destination) throws ElementNotFoundException {
        Piece piece = pieceService.getPiece(pieceId);
        boolean takenPiece = pieceService.getPieceForGameAndPosition(destination.getXPos(),
                destination.getYPos(), game.getId()) != null;
        Move newMove = moveService.getOrCreateMove(piece, destination, takenPiece);
        getMoveDetails(newMove);
        return moveService.saveMoveForPlayer(newMove, playerInTurn(game));
    }

    private void getMoveDetails(Move move) {
        CastleType castleType = getTypeIfCastled(move);
        PieceType promotionPiece = getNewPieceIfPromoted(move);
        moveService.updateSpecialMove(move, castleType, promotionPiece);
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

    private void checkIfPieceInTurn(Game game, long pieceId) throws ElementNotFoundException, InvalidMoveException {
        Piece piece = pieceService.getPiece(pieceId);
        if ((game.getGameState() == GameState.WHITE_TURN && piece.getPlayer().getTeam() == Team.WHITE)
                || (game.getGameState() == GameState.BLACK_TURN && piece.getPlayer().getTeam() == Team.BLACK)) {
            return;
        }
        throw new InvalidMoveException("Not players turn!");
    }

    private void updatePieceAfterMove(Move move) throws ElementNotFoundException {
        if (move.getPromotedTo() != null)
            pieceService.promotePawnTo(move);
        else
            pieceService.updatePosition(move);
    }

    private void processMove(Game game) throws ElementNotFoundException {
        setOtherDraws(game);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
        // TODO check for check
    }

    private void updatePlayerTurn(Game game) {
        GameState nextPlayerTurn = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN
                : GameState.WHITE_TURN;
        game.setGameState(nextPlayerTurn);
    }

    private void setCheckOrStaleMate(Game game) throws ElementNotFoundException {
        Player playerInTurn = playerInTurn(game);
        if (!canPlayerMove(playerInTurn)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
    }

    private Player playerInTurn(Game game) throws ElementNotFoundException {
        if (game.getGameState() == GameState.WHITE_TURN)
            return game.getWhitePlayer();
        else if (game.getGameState() == GameState.BLACK_TURN)
            return game.getBlackPlayer();
        else
            throw new ElementNotFoundException("No Player in turn!");
    }

    private boolean isPlayerInCheck(Player player) throws ElementNotFoundException {
        King playerKing = playerService.getPlayerKing(player);
        kingService.setup(playerKing, player.getGame().getId());
        return kingService.isInCheck();
    }

    private boolean canPlayerMove(Player player) throws ElementNotFoundException {
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

    private void setOtherDraws(Game game) throws ElementNotFoundException {
        if (hasFiftyMoveDraw(game) || hasThreeFoldDraw(game))
            game.setGameState(GameState.DRAW);
    }

    private boolean hasFiftyMoveDraw(Game game) throws ElementNotFoundException {
        return playerHasFiftyMoveDraw(playerInTurn(game));
    }

    private boolean playerHasFiftyMoveDraw(Player player) {
        if (playerService.getNumberOfMoves(player.getId()) < 50)
            return false;

        List<Move> lastFiftyMoves = playerService.getLastNMoves(50, player.getId());
        for (Move move : lastFiftyMoves) {
            if (move.getPiece() instanceof Pawn || move.isTakenPiece()) {
                return false;
            }
        }
        return true;
    }
}
