package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.controller.dto.GameDto;
import com.practice.project.chess.controller.dto.MovePieceDto;
import com.practice.project.chess.controller.dto.mapper.GameDtoMapper;
import com.practice.project.chess.repository.dao.GameDao;
import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.mapper.GameMapper;
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

import static com.practice.project.chess.service.logic.game.util.PlayerUtil.getPlayerPieceOnCoordinate;

@AllArgsConstructor
@Service
public class GameService {

    private final PlayerService playerService;

    private final MakeMoveService makeMoveService;
    private final LegalMoveService legalMoveService;

    private final GameRepository gameRepository;

    private final GameDtoMapper gameDtoMapper;
    private final GameMapper gameMapper;

    public Game getGame(long id) {
        return gameRepository.findById(id)
                .map(gameMapper::gameDaoToGame)
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
        GameDao game = new GameDao();
        PlayerDao whitePlayer = PlayerDao.builder().team(Team.WHITE).build();
        PlayerDao blackPlayer = PlayerDao.builder().team(Team.BLACK).build();

        processNewPlayers(game, whitePlayer, blackPlayer);
        return gameDtoMapper.gameToGameDto(gameRepository.save(game));
    }

    private void processNewPlayers(GameDao game, PlayerDao whitePlayer, PlayerDao blackPlayer) {
        playerService.setStartPiecesForPlayer(whitePlayer);
        playerService.setStartPiecesForPlayer(blackPlayer);

        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setGameState(GameState.WHITE_TURN);
    }

    public void processMoveRequest(MovePieceDto dto) {
        // TODO game and gamedao should be separated, idem for player and playerdao
        Game game = getGame(dto.getGameId());
        setLegalMoves(game);

        Piece piece = getPieceForTeamAndPosition(game, dto.getTeam(), new Coordinate(dto.getXFrom(), dto.getYFrom()));
        Coordinate destination = new Coordinate(dto.getXTo(), dto.getYTo());
        makeMoveService.makeMove(game, piece, destination);
        // TODO: legal moves should be set for the game before the move
    }

    private void setLegalMoves(Game game) {
        for (Player player : Set.of(game.getWhitePlayer(), game.getBlackPlayer())) {
            legalMoveService.setAllLegalMovableSquaresForPlayer(player, game);
        }
    }

    public static Piece getPieceForTeamAndPosition(Game game, Team team, Coordinate coordinate) {
        return getPlayerPieceOnCoordinate(getPlayerOfTeam(game, team), coordinate);
    }
}
