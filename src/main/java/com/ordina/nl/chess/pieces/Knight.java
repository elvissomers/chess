package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.MovementType;

import java.util.Set;

public class Knight extends Piece {

    public Knight() {
        super();
    }

    public Knight(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.LSHAPED));
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
