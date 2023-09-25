package com.practice.project.chess.entity.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("KNIGHT")
public class Knight extends Piece {

    public Knight() {
        super();
    }

    public Knight(Piece other){
        super(other);
    }

    public Knight copy(){
        return new Knight(this);
    }

    @Override
    public String toString(){
        return "N";
    }
}
