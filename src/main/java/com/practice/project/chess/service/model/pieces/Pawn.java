package com.practice.project.chess.service.model.pieces;

import com.practice.project.chess.service.structures.Coordinate;
import org.springframework.stereotype.Component;


@Component
public class Pawn extends Piece {

    public Pawn() {
        super();
    }

    public Pawn(Piece other){
        super(other);
    }

    public void addAttackedSquare(Coordinate square) {
        getAttackedSquares().add(square);
    }

    public Pawn copy(){
        return new Pawn(this);
    }

    @Override
    public String toString(){
        return "";
    }
}
