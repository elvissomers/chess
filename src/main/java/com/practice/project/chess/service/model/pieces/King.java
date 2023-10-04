package com.practice.project.chess.service.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("KING")
public class King extends Piece {

    private boolean hasMoved;

    private boolean inCheck;

    public King() {
        super();
    }

    public King(Piece other){
        super(other);
    }

    public King copy(){
        return new King(this);
    }

    @Override
    public String toString(){
        return "K";
    }
}
