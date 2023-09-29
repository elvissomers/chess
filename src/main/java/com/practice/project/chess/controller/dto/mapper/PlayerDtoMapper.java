package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.PlayerDto;
import com.practice.project.chess.controller.dto.PlayerPiecesDto;
import com.practice.project.chess.repository.entity.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerDtoMapper {

    private final PieceDtoMapper pieceDtoMapper;

    public PlayerDto playerToPlayerDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .team(player.getTeam())
                .gameId(player.getGame().getId())
                .playerPiecesDto(getPlayerPiecesDto(player))
                .build();
    }

    private PlayerPiecesDto getPlayerPiecesDto(Player player) {
        return PlayerPiecesDto.builder()
                .pieces(player.getPieces().stream()
                        .map(pieceDtoMapper::pieceToPieceDto)
                        .toList()
                )
                .build();
    }
}
