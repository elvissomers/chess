package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.data.dto.mapper.GameDtoMapper;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;

    private final GameDtoMapper gameDtoMapper;

    public Game getGame(long id) {
        return gameRepository.findById(id)
                .orElse(null);
        // TODO; when BaseExceptionHandler is put up:
//                .orElseThrow(() -> new ElementNotFoundException("Requested game ID does not correspond to an existing game!"));;
    }

    public GameDto getGameDto(long id) {
        return gameRepository.findById(id)
                .map(gameDtoMapper::gameToGameDto)
                .orElse(null);
        // TODO; when BaseExceptionHandler is put up:
//                .orElseThrow(() -> new ElementNotFoundException("Requested game ID does not correspond to an existing game!"));
    }
}
