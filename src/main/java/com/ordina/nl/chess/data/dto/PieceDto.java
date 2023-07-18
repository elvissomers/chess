package com.ordina.nl.chess.data.dto;

import com.ordina.nl.chess.enums.PieceType;
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
    @Enumerated(EnumType.STRING)
    private PieceType pieceType;
}
