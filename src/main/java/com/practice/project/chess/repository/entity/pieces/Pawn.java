package com.practice.project.chess.repository.entity.pieces;

import com.practice.project.chess.service.structures.Coordinate;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue("PAWN")
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
