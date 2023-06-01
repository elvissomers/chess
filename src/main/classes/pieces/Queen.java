package main.classes.pieces;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.Set;

public class Queen extends Piece{

    public Queen(Game game, Team team) {
        super(game, team);
    }

    public Queen(Piece other){
        super(other);
    }

    public Queen(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL, MovementType.DIAGONAL));
    }

    public Queen copy(){
        return new Queen(this);
    }
}
