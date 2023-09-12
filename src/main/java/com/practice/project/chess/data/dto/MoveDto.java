package com.practice.project.chess.data.dto;

import com.practice.project.chess.enums.CastleType;
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

    private boolean takenPiece;
    @Enumerated(EnumType.STRING)
    private CastleType castleType;
}
