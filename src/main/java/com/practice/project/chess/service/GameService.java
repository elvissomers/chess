package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.data.dto.mapper.GameDtoMapper;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Move;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.GameState;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.exception.InvalidMoveException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.pieces.KingService;
import com.practice.project.chess.service.pieces.PieceService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GameService {

    private final PieceService pieceService;
    private final PlayerService playerService;
    private final BoardService boardService;
    private final KingService kingService;

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

        playerService.setStartPiecesForPlayer(whitePlayer);
        playerService.setStartPiecesForPlayer(blackPlayer);

        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setGameState(GameState.WHITE_TURN);
        return gameDtoMapper.gameToGameDto(gameRepository.save(game));
    }

    public void makeMove(long gameId, long pieceId, Coordinate destination)
            throws ElementNotFoundException, InvalidMoveException {
        Game game = getGame(gameId);
        pieceService.checkMoveLegality(pieceId, destination);
        checkIfPieceInTurn(game, pieceId);
        pieceService.updatePosition(pieceId, destination);
    }

    private void checkIfPieceInTurn(Game game, long pieceId) throws ElementNotFoundException, InvalidMoveException {
        Piece piece = pieceService.getPiece(pieceId);
        if ((game.getGameState() == GameState.WHITE_TURN && piece.getPlayer().getTeam() == Team.WHITE)
                || (game.getGameState() == GameState.BLACK_TURN && piece.getPlayer().getTeam() == Team.BLACK)) {
            return;
        }
        throw new InvalidMoveException("Not players turn!");
    }

    private void processMove(long gameId) throws ElementNotFoundException {
        Game game = getGame(gameId);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
        // TODO: check for other draws
        // TODO: saving moves to moveHistory
        // TODO (Later) : check for check
    }

    private void updatePlayerTurn(Game game) {
        GameState nextPlayerTurn = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN
                : GameState.WHITE_TURN;
        game.setGameState(nextPlayerTurn);
    }

    private void setCheckOrStaleMate(Game game) throws ElementNotFoundException {
        // TODO : player in turn needs to be updated before this method is called
        Player playerInTurn = getPlayerInTurn(game);
        if (!canPlayerMove(playerInTurn)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
    }

    private Player getPlayerInTurn(Game game) throws ElementNotFoundException {
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
        // TODO: we only need to check for the player in turn
        // TODO: this is not how three fold draw works! We need both players to repeat the moves!
        return (playerHasThreeFoldDraw(game.getWhitePlayer()) && playerHasThreeFoldDraw(game.getBlackPlayer()));
    }

    private boolean playerHasThreeFoldDraw(Player player) {
        int historySize = player.getMoveHistory().size();
        if (historySize < 6)
            return false;
        // TODO: update move object and dto

        List<Move> lastSixMoves = player.getLastNMoves(6);
        return (goesBackAndForth(lastSixMoves));
    }

    private boolean goesBackAndForth(List<Move> moves) {
        return (moves.get(0) == moves.get(2) && moves.get(2) == moves.get(4) &&
                moves.get(1) == moves.get(3) && moves.get(3) == moves.get(5));
    }

    private boolean hasFiftyMoveDraw(Game game) {
        // TODO: we only need to check for the player in turn
        return (playerHasFiftyMoveDraw(game.getWhitePlayer()) || playerHasFiftyMoveDraw(game.getBlackPlayer()));
    }

    private boolean playerHasFiftyMoveDraw(Player player) {
        if (player.getMoveHistory().size() < 50)
            return false;

        List<Move> lastFiftyMoves = player.getLastNMoves(50);
        for (Move move : lastFiftyMoves) {
            if (move.getPiece() instanceof Pawn || move.getTakenPiece() != null) {
                return false;
            }
        }
        return true;
    }
}
