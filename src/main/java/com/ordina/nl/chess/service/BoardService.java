package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BoardService {

    private final GameRepository gameRepository;

    private final GameService gameService;

    public BoardMap getBoardMapForGame(GameDto gameDto) {
        return new BoardMap();
    }
}
