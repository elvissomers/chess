package com.practice.project.chess.data.dto;

import com.practice.project.chess.service.structures.Coordinate;
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
