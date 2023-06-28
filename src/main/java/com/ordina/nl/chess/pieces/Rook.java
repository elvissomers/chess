package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Rook extends Piece {

    private boolean hasMoved;

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

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
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
