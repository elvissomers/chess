package com.practice.project.chess.service.model.pieces;

import org.springframework.stereotype.Component;

@Component
public class Bishop extends Piece {

    public Bishop() {
        super();
    }

    // TODO change type from Piece to Bishop (idem) other classes
    public Bishop(Piece other){
        super(other);
    }

    public Bishop copy(){
        return new Bishop(this);
    }

    @Override
    public String toString(){
        return "B";
    }
}
