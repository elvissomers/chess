package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.GameDto;
import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.service.model.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class GameDtoMapper {

    private final PieceDtoMapper pieceDtoMapper;

    public GameDto gameToGameDto(Game game) {
        return GameDto.builder()
                .gameState(game.getGameState())
                .whitePlayerId(game.getWhitePlayer().getId())
                .blackPlayerId(game.getBlackPlayer().getId())
                .pieces(getPieces(game))
                .build();
    }

    private List<PieceDto> getPieces(Game game) {
        return Stream.concat(game.getBlackPlayer().getPieces().stream(),
                        game.getWhitePlayer().getPieces().stream())
                .map(pieceDtoMapper::pieceToPieceDto)
                .toList();
    }
}
