package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.structures.MovementType;

import java.util.Set;

public class King extends Piece {

    public King(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.KING));
    }

    public King(Piece other){
        super(other);
    }

    // King keeps track of whether it has moved during this game
    // - needed for castling rule
    // TODO: will remove this, obtain it from the move history instead
    private boolean hasMoved;

    private boolean inCheck;

    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }


    /**
     * Separate checkCheck method that is used for checking a hypothetical board,
     * that is, a board that does not belong to a Game and.
     *
     * It does not need a Square as input, as it only checks if the King is in check
     * on the square that it currently is on.
     */
//    public boolean checkCheck(Board board){
//        return false;
//    }

    public King copy(){
        return new King(this);
    }
}
