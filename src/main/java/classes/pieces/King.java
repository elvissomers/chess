package classes.pieces;

import classes.instances.Player;
import classes.structures.Coordinate;
import classes.structures.MovementType;
import classes.structures.Team;

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
