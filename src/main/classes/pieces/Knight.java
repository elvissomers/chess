package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.Set;

public class Knight extends Piece {

    public Knight(Game game, Team team) {
        super(game, team);
    }

    public Knight(Piece other){
        super(other);
    }

    public Knight(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.LSHAPED));
    }

    public Knight copy(){
        return new Knight(this);
    }
}
