package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;
import com.ordina.nl.chess.structures.Team;

import java.util.Set;

public class King extends Piece {

    public King() {
        super();
    }

    // TODO: have optional fields present in Piece (but null in pieces that don't have it)?
    private boolean hasMoved;

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

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.KING);
    }

    @Override
    public String toString(){
        return "K";
    }

    // TODO: this might be redundant? The whole "check" attribute?
    public boolean isInCheck(){
        Player attackingPlayer = (getPlayer().getTeam() == Team.WHITE) ? getPlayer().getGame().getBlackPlayer() :
                getPlayer().getGame().getWhitePlayer();
        Set<Coordinate> allAttackedPositions = attackingPlayer.getAllAttackedSquares();
        Coordinate kingCoordinate = new Coordinate(getHorizontalPosition(), getVerticalPosition());
        return allAttackedPositions.contains(kingCoordinate);
    }

}
