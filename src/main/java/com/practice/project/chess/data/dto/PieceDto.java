package com.practice.project.chess.data.dto;

import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.enums.Team;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PieceDto {

    private int horizontalPosition;
    private int verticalPosition;

    @Enumerated(EnumType.STRING)
    private PieceType pieceType;
    @Enumerated(EnumType.STRING)
    private Team team;
}
