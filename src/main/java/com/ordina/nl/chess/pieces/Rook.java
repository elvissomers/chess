package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

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
