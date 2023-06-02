package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.structures.MovementType;

import java.util.Set;

public class King extends Piece {

    private boolean hasMoved;

    private boolean inCheck;

    public King(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.KING));
    }

    public King(Piece other){
        super(other);
    }

    public King copy(){
        return new King(this);
    }

    public boolean isInCheck() {
        return inCheck;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setInCheck(){

    }

}
