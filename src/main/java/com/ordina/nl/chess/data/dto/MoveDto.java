package com.ordina.nl.chess.data.dto;

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
}
