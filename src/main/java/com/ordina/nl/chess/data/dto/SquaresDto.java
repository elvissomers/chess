package com.ordina.nl.chess.data.dto;

import com.ordina.nl.chess.service.structures.Coordinate;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SquaresDto {

    private List<Coordinate> squares;
}
