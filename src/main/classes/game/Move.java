package main.classes.game;

import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.Coordinate;
import main.classes.structures.PieceType;

public class Move {

    private final Piece piece;

    private final Coordinate squareFrom;

    private final Coordinate squareTo;

    private final Piece takenPiece;

    private final CastleType castleType;

    // TODO: piece or piecetype here?
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
}
