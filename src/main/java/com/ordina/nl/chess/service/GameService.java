package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.data.dto.mapper.GameDtoMapper;
import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.repository.GameRepository;
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
