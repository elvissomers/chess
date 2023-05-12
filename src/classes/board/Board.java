package classes.board;

import classes.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int verticalSize;

    private int horizontalSize;

    // TODO: find out what data structure best to use (for now, ArrayList)
    // Perhaps I should use another data structure to implement getSquareByPos more easily
    private Square[][] squares = new Square[horizontalSize][verticalSize];

    public Board(int verticalSize, int horizontalSize) {
        this.verticalSize = verticalSize;
        this.horizontalSize = horizontalSize;
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
            for(int yPos = 0; yPos < verticalSize; xPos++){
                Square square = new Square(xPos, yPos);
                squares[xPos][yPos] = square;
            }
        }

        this.squares = squares;
    }

    public void setPiece(Square square, Piece piece){
        square.setPiece(piece);
    }

    public Square getSquareByPos(int horizontalPosition, int verticalPosition){

    }
}
