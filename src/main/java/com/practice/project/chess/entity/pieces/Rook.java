package com.practice.project.chess.entity.pieces;

import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.MovementType;
import com.practice.project.chess.enums.PieceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("ROOK")
public class Rook extends Piece {

    public Rook() {
        super();
    }

    public Rook(Player player) {
        super(player);
        this.setMoveRules(Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL));
    }

    public Rook(Piece other){
        super(other);
    }

    public Rook copy(){
        return new Rook(this);
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.ROOK);
    }

    @Override
    public String toString(){
        return "R";
    }
}
