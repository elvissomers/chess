package com.practice.project.chess.data.dto;

import com.practice.project.chess.enums.CastleType;
import com.practice.project.chess.enums.PieceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoveDto {

    private long id;
    private long pieceId;

    private int horizontalFrom;
    private int verticalFrom;
    private int horizontalTo;
    private int verticalTo;

    private long takenPieceId;
    @Enumerated(EnumType.STRING)
    private CastleType castleType;
    @Enumerated(EnumType.STRING)
    private PieceType promotedTo;
}
