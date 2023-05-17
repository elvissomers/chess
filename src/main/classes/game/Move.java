package main.classes.game;

import main.classes.board.Square;
import main.classes.pieces.Piece;

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
}
