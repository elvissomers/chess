package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.structures.MovementType;

import java.util.Set;

public class Knight extends Piece {

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
