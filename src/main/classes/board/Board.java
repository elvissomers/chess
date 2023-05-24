package main.classes.board;

import main.classes.pieces.Piece;

import java.util.Arrays;

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

    public Board(Board other){
        this.verticalSize = other.getVerticalSize();
        this.horizontalSize = other.getHorizontalSize();

        Square[][] copySquares = new Square[horizontalSize][verticalSize];
        for (int i = 0; i < copySquares.length; i++) {
            copySquares[i] = Arrays.copyOf(other.getSquares()[i], copySquares.length);
        }
        this.squares = copySquares;
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
