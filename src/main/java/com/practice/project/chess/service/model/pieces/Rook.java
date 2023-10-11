package com.practice.project.chess.service.model.pieces;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
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
