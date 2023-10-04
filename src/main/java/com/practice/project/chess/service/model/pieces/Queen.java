package com.practice.project.chess.service.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("QUEEN")
public class Queen extends Piece{

    public Queen() {
        super();
    }

    public Queen(Piece other){
        super(other);
    }

    public Queen copy(){
        return new Queen(this);
    }

    @Override
    public String toString(){
        return "Q";
    }
}
