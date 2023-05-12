package classes.board;

import classes.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int verticalSize;

    private int horizontalSize;

    // TODO: find out what data structure best to use (for now, ArrayList)
    private List<Square> squares = new ArrayList<>();

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

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares() {
        List<Square> squares = new ArrayList<>();
        for(int xPos = 1; xPos < horizontalSize + 1; xPos++){
            for(int yPos = 1; yPos < verticalSize + 1; xPos++){
                Square square = new Square(xPos, yPos);
                squares.add(square);
            }
        }

        this.squares = squares;
    }

    public void setPiece(Square square, Piece piece){
        square.setPiece(piece);
    }
}
