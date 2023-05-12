package classes.pieces;

import classes.board.Board;
import classes.board.Square;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private Square square;

    private Board board;

    private enum team {
        WHITE, BLACK
    }

    private List<Square> moveableSquares = new ArrayList<>();

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Square> getMoveableSquares() {
        return moveableSquares;
    }

    public abstract void setMoveableSquares();
}
