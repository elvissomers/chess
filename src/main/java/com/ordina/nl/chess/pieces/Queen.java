package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;

import java.util.Set;

public class Queen extends Piece{

    public Queen() {
        super();
    }

    public Queen(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL, MovementType.DIAGONAL));
    }

    public Queen(Piece other){
        super(other);
    }

    public Queen copy(){
        return new Queen(this);
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.QUEEN);
    }

    @Override
    public String toString(){
        return "Q";
    }
}
