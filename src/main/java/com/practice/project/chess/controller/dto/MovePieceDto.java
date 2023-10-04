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
public class MovePieceDto {

    private long gameId;
    @Enumerated(EnumType.ORDINAL)
    private Team team;

    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
}
