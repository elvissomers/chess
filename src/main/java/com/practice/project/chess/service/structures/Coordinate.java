package com.practice.project.chess.service.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Coordinate coordinate = (Coordinate) obj;

        return xPos == coordinate.getXPos() && yPos == coordinate.getYPos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }
}
