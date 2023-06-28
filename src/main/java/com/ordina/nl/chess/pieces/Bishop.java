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
public class Bishop extends Piece {

    public Bishop() {
        super();
    }

    public Bishop(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.DIAGONAL));
    }

    // TODO change type from Piece to Bishop (idem) other classes
    public Bishop(Piece other){
        super(other);
    }

    public Bishop copy(){
        return new Bishop(this);
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.BISHOP);
    }

    @Override
    public String toString(){
        return "B";
    }
}
