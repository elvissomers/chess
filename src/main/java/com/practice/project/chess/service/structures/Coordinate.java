package com.practice.project.chess.service.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {

    int xPos;
    int yPos;

    @Override
    public String toString(){
        char[] horizontalIndices = {'a','b','c','d','e','f','g','h'};
        String verticalIndex = Integer.toString(yPos+1);
        return horizontalIndices[xPos] + verticalIndex;
    }
}
