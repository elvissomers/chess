package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.enums.*;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue("PAWN")
public class Pawn extends Piece {

    public Pawn() {
        super();
    }

    public Pawn(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.PAWN));
    }

    public Pawn(Piece other){
        super(other);
    }

    /*
     * Unlike any of the other pieces, the pawn has specific squares they attack,
     * without necessarily being able to move to these squares.
     * We need to keep track of these squares to be able to see if there are
     * checks, checkmates or stalemates.
     */
    // TODO : promotion

    public void setAttackedSquares(BoardMap board) {
        int xPos = this.getHorizontalPosition();
        int yPos = this.getVerticalPosition();
        int xSize = 8;
        int yDirection = (this.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;

        if (xPos + 1 < xSize) {
            Coordinate squareInFrontRight = board.getCoordinateByPos(xPos + 1, yPos + yDirection);
            this.getAttackedSquares().add(squareInFrontRight);
        }

        if (xPos > 0) {
            Coordinate squareInFrontLeft = board.getCoordinateByPos(xPos - 1, yPos + yDirection);
            this.getAttackedSquares().add(squareInFrontLeft);
        }
    }


    public Pawn copy(){
        return new Pawn(this);
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.PAWN);
    }

    @Override
    public String toString(){
        return "";
    }
}
