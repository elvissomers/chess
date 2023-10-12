package com.practice.project.chess.service.model.pieces;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class King extends Piece {

    private boolean hasMoved;

    // TODO: in Check should not be saved in the database, so should always be obtained
    // TODO after converting dao to King
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
