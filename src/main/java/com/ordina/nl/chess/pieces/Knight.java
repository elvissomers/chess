package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

@Entity
@DiscriminatorValue("KNIGHT")
public class Knight extends Piece {

    public Knight() {
        super();
    }

    public Knight(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.LSHAPED));
    }

    public Knight(Piece other){
        super(other);
    }

    public Knight copy(){
        return new Knight(this);
    }

    @Override
    public void setCorrectPieceType() {
        setPieceType(PieceType.KNIGHT);
    }

    @Override
    public String toString(){
        return "N";
    }
}
