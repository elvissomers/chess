package com.practice.project.chess.entity.pieces;

import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.MovementType;
import com.practice.project.chess.enums.PieceType;
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
