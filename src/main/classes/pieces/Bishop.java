package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.Set;

public class Bishop extends Piece {

    public Bishop(Game game, Team team) {
        super(game, team);
    }

    // TODO change type from Piece to Bishop (idem) other classes
    public Bishop(Piece other){
        super(other);
    }

    public Bishop(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.DIAGONAL));
    }

    public Bishop copy(){
        return new Bishop(this);
    }
}
