package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.controller.dto.GameDto;
import com.practice.project.chess.controller.dto.MovePieceDto;
import com.practice.project.chess.controller.dto.mapper.GameDtoMapper;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.practice.project.chess.service.logic.player.PlayerService.getPlayerPieceOnCoordinate;

@AllArgsConstructor
@Service
public class GameService {

    private final PlayerService playerService;

    private final MakeMoveService makeMoveService;
    private final LegalMoveService legalMoveService;

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

    private static Player getPlayerOfTeam(Game game, Team team) {
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }

    public GameDto getNewGame() {
        Game game = new Game();
        Player whitePlayer = Player.builder().game(game).team(Team.WHITE).build();
        Player blackPlayer = Player.builder().game(game).team(Team.BLACK).build();

        processNewPlayers(game, whitePlayer, blackPlayer);
        setLegalMoves(game);
        return gameDtoMapper.gameToGameDto(gameRepository.save(game));
    }

    private void processNewPlayers(Game game, Player whitePlayer, Player blackPlayer) {
        playerService.setStartPiecesForPlayer(whitePlayer);
        playerService.setStartPiecesForPlayer(blackPlayer);

        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setGameState(GameState.WHITE_TURN);
    }

    private void setLegalMoves(Game game) {
        for (Player player : Set.of(game.getWhitePlayer(), game.getBlackPlayer())) {
            for (Piece piece : player.getPieces())
                legalMoveService.setLegalMovableSquaresForPiece(piece, game);
        }
    }

    public void makeMoveFromDto(MovePieceDto dto) {
        Game game = getGame(dto.getGameId());
        Piece piece = getPieceForTeamAndPosition(game, dto.getTeam(), new Coordinate(dto.getXFrom(), dto.getYFrom()));
        Coordinate destination = new Coordinate(dto.getXTo(), dto.getYTo());

        makeMoveService.makeMove(game, piece, destination);
        // TODO: legal moves should be set for the game after the move
    }

    public static Piece getPieceForTeamAndPosition(Game game, Team team, Coordinate coordinate) {
        return getPlayerPieceOnCoordinate(getPlayerOfTeam(game, team), coordinate);
    }
}
