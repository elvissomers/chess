package com.ordina.nl.chess.game;

import com.ordina.nl.chess.structures.CastleType;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.pieces.Piece;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int number;

    @Column(nullable = false)
    private final Piece piece;

    @Column(nullable = false)
    private final Coordinate squareFrom;

    @Column(nullable = false)
    private final Coordinate squareTo;

    @Column
    private final Piece takenPiece;

    @Column
    private final CastleType castleType;

    // TODO: piece or piecetype here?
    @Column
    private final boolean promoted;

    public Move(Piece piece, Coordinate squareFrom, Coordinate squareTo, Piece takenPiece, CastleType castleType,
                boolean promoted) {
        this.piece = piece;
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
        this.takenPiece = takenPiece;
        this.castleType = castleType;
        this.promoted = promoted;
    }

    public Piece getPiece() {
        return piece;
    }

    public Coordinate getSquareFrom() {
        return squareFrom;
    }

    public Coordinate getSquareTo() {
        return squareTo;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public CastleType getCastleType() {
        return castleType;
    }

    public boolean isPromoted() {
        return promoted;
    }

    @Override
    public String toString(){
        if (castleType == CastleType.SHORT)
            return "0-0";
        else if (castleType == CastleType.LONG)
            return "0-0-0";

        String moveChar = (takenPiece == null) ? "-" : "x";
        return piece.toString() + squareFrom.toString() + moveChar + squareTo.toString();
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

        return (Objects.equals(piece, move.piece)) && (Objects.equals(squareFrom, move.squareFrom))
                && (Objects.equals(squareTo, move.squareTo));
    }

    @Override
    public int hashCode() {
        int result = piece != null ? piece.hashCode() : 0;
        result = 31 * result + (squareFrom != null ? squareFrom.hashCode() : 0);
        result = 31 * result + (squareTo != null ? squareTo.hashCode() : 0);
        return result;
    }

}
