package com.ordina.nl.chess.data.dto.mapper;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameDtoMapper {

    public GameDto gameToGameDto(Game game) {
        return GameDto.builder()
                .gameState(game.getGameState())
                .id(game.getId())
                .whitePlayerId(game.getWhitePlayer().getId())
                .blackPlayerId(game.getBlackPlayer().getId())
                .build();
    }
}
