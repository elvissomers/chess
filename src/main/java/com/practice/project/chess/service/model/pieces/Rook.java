package com.practice.project.chess.service.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@DiscriminatorValue("ROOK")
public class Rook extends Piece {

    private boolean hasMoved;

    public Rook() {
        super();
    }

    public Rook(Piece other){
        super(other);
    }

    public Rook copy(){
        return new Rook(this);
    }

    @Override
    public String toString(){
        return "R";
    }
}
