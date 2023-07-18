package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.enums.Coordinate;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.enums.Team;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("KING")
public class King extends Piece {

    public King() {
        super();
    }

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
