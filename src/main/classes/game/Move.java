package main.classes.game;

import main.classes.board.Square;
import main.classes.pieces.*;

public class Move {

    private Piece piece;

    private Square squareFrom;

    private Square squareTo;

    public Move(Piece piece, Square squareFrom, Square squareTo) {
        this.piece = piece;
        this.squareFrom = squareFrom;
        this.squareTo = squareTo;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getSquareFrom() {
        return squareFrom;
    }

    public Square getSquareTo() {
        return squareTo;
    }

    @Override
    public String toString(){
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
