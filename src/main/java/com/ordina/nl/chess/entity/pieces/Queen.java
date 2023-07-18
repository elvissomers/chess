package com.ordina.nl.chess.entity.pieces;

import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("QUEEN")
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
