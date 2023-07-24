package com.practice.project.chess.data.dto.mapper;

import com.practice.project.chess.data.dto.GameDto;
import com.practice.project.chess.entity.Game;
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
