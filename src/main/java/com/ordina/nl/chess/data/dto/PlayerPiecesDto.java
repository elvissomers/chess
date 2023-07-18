package com.ordina.nl.chess.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPiecesDto {

    private List<PieceDto> pieces;
}
