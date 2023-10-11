package com.practice.project.chess.service.model.pieces;

import org.springframework.stereotype.Component;

@Component
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
