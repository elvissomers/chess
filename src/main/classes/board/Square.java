package main.classes.board;

import main.classes.pieces.Piece;

public class Square {
    private int horizontalPosition;

    private int verticalPosition;

    private Piece piece;

    public Square(int horizontalPosition, int verticalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString(){
        char[] horizontalIndices = {'a','b','c','d','e','f','g','h'};
        String verticalIndex = Integer.toString(verticalPosition+1);
        return horizontalIndices[horizontalPosition] + verticalIndex;
    }

    // TODO: helper methods, squareup, squaredown, squareleft, squareright
}
