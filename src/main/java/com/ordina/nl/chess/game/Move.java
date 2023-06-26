package com.ordina.nl.chess.game;

import com.ordina.nl.chess.structures.CastleType;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.pieces.Piece;
import jakarta.persistence.*;

import java.util.Objects;

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

    @Column(nullable = true)
    private Piece takenPiece;

    @Column(nullable = true)
    private CastleType castleType;

    // TODO: piece or piecetype here?
    @Column
    private boolean promoted;

    public Move() {
    }

    public Move(Piece piece, Coordinate squareFrom, Coordinate squareTo, Piece takenPiece, CastleType castleType,
                boolean promoted) {
        this.piece = piece;
        this.horizontalFrom = squareFrom.getX();
        this.verticalFrom = squareFrom.getY();
        this.horizontalTo = squareTo.getX();
        this.verticalTo = squareTo.getY();
        this.takenPiece = takenPiece;
        this.castleType = castleType;
        this.promoted = promoted;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public CastleType getCastleType() {
        return castleType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public int getHorizontalFrom() {
        return horizontalFrom;
    }

    public int getVerticalFrom() {
        return verticalFrom;
    }

    public int getHorizontalTo() {
        return horizontalTo;
    }

    public int getVerticalTo() {
        return verticalTo;
    }

    public void setHorizontalFrom(int horizontalFrom) {
        this.horizontalFrom = horizontalFrom;
    }

    public void setVerticalFrom(int verticalFrom) {
        this.verticalFrom = verticalFrom;
    }

    public void setHorizontalTo(int horizontalTo) {
        this.horizontalTo = horizontalTo;
    }

    public void setVerticalTo(int verticalTo) {
        this.verticalTo = verticalTo;
    }

    @Override
    public String toString(){
        if (castleType == CastleType.SHORT)
            return "0-0";
        else if (castleType == CastleType.LONG)
            return "0-0-0";

        String moveChar = (takenPiece == null) ? "-" : "x";
        Coordinate squareFrom = new Coordinate(horizontalFrom, verticalFrom);
        Coordinate squareTo = new Coordinate(horizontalTo, verticalTo);
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

        return (Objects.equals(piece, move.piece) && horizontalFrom == move.horizontalFrom &&
                horizontalTo == move.horizontalTo && verticalFrom == move.verticalFrom &&
                verticalTo == move.verticalTo && number == move.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, horizontalFrom, horizontalTo, verticalFrom, verticalTo, number);
    }

}
