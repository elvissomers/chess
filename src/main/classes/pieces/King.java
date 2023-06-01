package main.classes.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.game.Move;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.List;
import java.util.Set;

public class King extends Piece {

    public King(Game game, Team team) {
        super(game, team);
    }

    public King(Piece other){
        super(other);
    }

    public King(King other){
        super(other.getGame(), other.getTeam());
    }

    public King(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.KING));
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
    public boolean checkCheck(Board board){
        return false;
    }

    public King copy(){
        return new King(this);
    }
}
