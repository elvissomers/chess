package main.classes.board;

import main.classes.pieces.Piece;

public class Board {

    private int verticalSize;

    private int horizontalSize;

    private Square[][] squares;

    public Board(int verticalSize, int horizontalSize) {
        this.verticalSize = verticalSize;
        this.horizontalSize = horizontalSize;
        this.squares = new Square[horizontalSize][verticalSize];
        this.setSquares();
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public void setVerticalSize(int verticalSize) {
        this.verticalSize = verticalSize;
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public void setHorizontalSize(int horizontalSize) {
        this.horizontalSize = horizontalSize;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares() {
        for(int xPos = 0; xPos < horizontalSize; xPos++){
            for(int yPos = 0; yPos < verticalSize; yPos++){
                Square square = new Square(xPos, yPos);
                squares[xPos][yPos] = square;
            }
        }
    }

    public void setPiece(Square square, Piece piece){
        square.setPiece(piece);
    }

    public Square getSquareByPos(int xPos, int yPos){
        return this.squares[xPos][yPos];
    }
}
