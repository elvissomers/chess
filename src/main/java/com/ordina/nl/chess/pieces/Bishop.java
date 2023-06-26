package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.MovementType;

import java.util.Set;

public class Bishop extends Piece {

    public Bishop() {
        super();
    }

    public Bishop(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.DIAGONAL));
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
