package com.ordina.nl.chess.entity;

import com.ordina.nl.chess.enums.CastleType;
import com.ordina.nl.chess.service.structures.Coordinate;
import com.ordina.nl.chess.entity.pieces.Piece;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private Piece piece;

    @Column(nullable = false)
    private int horizontalFrom;

    @Column(nullable = false)
    private int verticalFrom;

    @Column(nullable = false)
    private int horizontalTo;

    @Column(nullable = false)
    private int verticalTo;

    private Piece takenPiece;
    private CastleType castleType;
    private boolean promoted;

    @Override
    public String toString(){
        if (castleType == CastleType.SHORT)
            return "0-0";
        else if (castleType == CastleType.LONG)
            return "0-0-0";

        String moveChar = (takenPiece == null) ? "-" : "x";
        Coordinate squareFrom = new Coordinate(horizontalFrom, verticalFrom);
        Coordinate squareTo = new Coordinate(horizontalTo, verticalTo);
        return piece.toString() + squareFrom + moveChar + squareTo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Move move = (Move) obj;

        return (Objects.equals(piece, move.piece) && horizontalFrom == move.horizontalFrom &&
                horizontalTo == move.horizontalTo && verticalFrom == move.verticalFrom &&
                verticalTo == move.verticalTo && number == move.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, horizontalFrom, horizontalTo, verticalFrom, verticalTo, number);
    }

}
