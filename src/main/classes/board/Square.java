package main.classes.board;

import main.classes.pieces.Piece;

public class Square implements Cloneable{
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

    @Override
    public Square clone() {
        try {
            Square clone = (Square) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // TODO: helper methods, squareup, squaredown, squareleft, squareright
}
