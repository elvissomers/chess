package com.practice.project.chess.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovePieceDto {

    private long gameId;

    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
}
