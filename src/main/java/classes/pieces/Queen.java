package main.java.classes.pieces;

import main.java.classes.instances.Player;
import main.java.classes.structures.MovementType;

import java.util.Set;

public class Queen extends Piece{

    public Queen(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL, MovementType.DIAGONAL));
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
