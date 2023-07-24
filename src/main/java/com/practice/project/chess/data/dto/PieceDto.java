package com.practice.project.chess.data.dto;

import com.practice.project.chess.enums.PieceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PieceDto {

    private long id;
    private long playerId;

    private int horizontalPosition;
    private int verticalPosition;
    @Enumerated(EnumType.STRING)
    private PieceType pieceType;
}
