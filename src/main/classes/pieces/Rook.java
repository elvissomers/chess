package main.classes.pieces;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.Set;

public class Rook extends Piece {

    // Rook keeps track of whether it has moved during this game
    // - needed for castling rule
    // TODO: implement this by looping through moveHistory
    // if hasMoved is true for both rooks OR for the king, there is no need
    // to do this anymore.
    // TODO: this should probably be implemented in the PieceSet class
    private boolean hasMoved;

    public Rook(Game game, Team team) {
        super(game, team);
    }

    public Rook(Piece other){
        super(other);
    }

    public Rook(Player player) {
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL));
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
}
