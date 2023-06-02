package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.structures.MovementType;

import java.util.Set;

public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(Player player) {
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL));
    }

    public Rook(Piece other){
        super(other);
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Rook copy(){
        return new Rook(this);
    }

    @Override
    public String toString(){
        return "R";
    }
}
