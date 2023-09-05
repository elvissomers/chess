package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.data.dto.mapper.GameDtoMapper;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {

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

    public Player getPlayerForGameAndTeam(long gameId, Team team) throws ElementNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Requested game ID does not correspond to an existing game!"));
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }
}
