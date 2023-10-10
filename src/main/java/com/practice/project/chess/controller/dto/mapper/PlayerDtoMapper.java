package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.PlayerDto;
import com.practice.project.chess.controller.dto.PlayerPiecesDto;
import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.service.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerDtoMapper {

    private final PieceDtoMapper pieceDtoMapper;

    public PlayerDto playerToPlayerDto(PlayerDao player) {
        return PlayerDto.builder()
                .id(player.getId())
                .team(player.getTeam())
                .playerPiecesDto(getPlayerPiecesDto(player))
                .build();
    }

    private PlayerPiecesDto getPlayerPiecesDto(PlayerDao player) {
        return PlayerPiecesDto.builder()
                .pieces(player.getPieces().stream()
                        .map(pieceDtoMapper::pieceToPieceDto)
                        .toList()
                )
                .build();
    }
}
