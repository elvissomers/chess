package com.practice.project.chess.controller.dto;

import com.practice.project.chess.repository.enums.Team;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private long id;
    private long gameId;
    private PlayerPiecesDto playerPiecesDto;

    @Enumerated(EnumType.STRING)
    private Team team;
}
