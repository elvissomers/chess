package main.java.classes.pieces;

import main.java.classes.instances.Player;
import main.java.classes.structures.Coordinate;
import main.java.classes.structures.MovementType;
import main.java.classes.structures.Team;

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

    @Override
    public String toString(){
        return "K";
    }

    public void setInCheck(){
        Player attackingPlayer = (getPlayer().getTeam() == Team.WHITE) ? getPlayer().getGame().getBlackPlayer() :
                getPlayer().getGame().getWhitePlayer();
        Set<Coordinate> allAttackedPositions = attackingPlayer.getAllAttackedSquares();
        inCheck = allAttackedPositions.contains(getPosition());
    }

}
