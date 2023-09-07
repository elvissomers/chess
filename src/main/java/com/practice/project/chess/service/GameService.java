package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.data.dto.mapper.GameDtoMapper;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.GameState;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.exception.InvalidMoveException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.pieces.PieceService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {

    private final PieceService pieceService;
    private final PlayerService playerService;
    private final BoardService boardService;

    private final GameRepository gameRepository;

    private final GameDtoMapper gameDtoMapper;

    public Game getGame(long id) throws ElementNotFoundException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Requested game ID does not correspond to an existing game!"));
    }

    public GameDto getGameDto(long id) throws ElementNotFoundException {
        return gameRepository.findById(id)
                .map(gameDtoMapper::gameToGameDto)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Requested game ID does not correspond to an existing game!"));
    }

    public GameDto getNewGame() {
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

    public Player getPlayerForGameAndTeam(long gameId, Team team) throws ElementNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Requested game ID does not correspond to an existing game!"));
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }

    public Player getOpponentPlayerForGameAndTeam(long gameId, Team team) throws ElementNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Requested game ID does not correspond to an existing game!"));
        return (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
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

    private void processMove(long gameId) {
        BoardMap boardAfterMove = boardService.getBoardMapForGame(gameId);
        // TODO: check for check, checkmate, stalemate, or other draws
    }

    private void updateGameStateAfterMove(Game game) {
        GameState newState = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN : GameState.WHITE_TURN;
        game.setGameState(newState);
    }
}
