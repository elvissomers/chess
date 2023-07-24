package com.practice.project.chess.data.dto;

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
