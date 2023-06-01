package main.classes.game;

import main.classes.pieces.*;
import main.classes.structures.Coordinate;

public class Move {

    private Piece piece;

    private Coordinate squareFrom;

    private Coordinate squareTo;

    public Move(Piece piece, Coordinate squareFrom, Coordinate squareTo) {
        this.piece = piece;
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
    }

    public Piece getPiece() {
        return piece;
    }

    public Coordinate getSquareFrom() {
        return squareFrom;
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
