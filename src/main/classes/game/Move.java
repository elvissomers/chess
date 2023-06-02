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
        // TODO: write this in piece classes, and call their toString() here
        String pieceChar = "";
        if (piece instanceof Knight)
            pieceChar = "N";
        else if (piece instanceof Bishop)
            pieceChar = "B";
        else if (piece instanceof Rook)
            pieceChar = "R";
        else if (piece instanceof Queen)
            pieceChar = "Q";
        else if (piece instanceof King)
            pieceChar = "K";

        return pieceChar + squareFrom.toString() + "-" + squareTo.toString();
    }
}
