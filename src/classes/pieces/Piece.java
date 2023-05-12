package classes.pieces;

import classes.board.Board;
import classes.board.Square;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private Square square;

    private Board board;

    public enum Team {
        WHITE, BLACK
    }

    private Team team;

    private List<Square> moveableSquares = new ArrayList<>();

    public Piece(Team team) {
        this.team = team;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Square> getMoveableSquares() {
        return moveableSquares;
    }

    public abstract void setMoveableSquares();

    public void addMovableSquare(Square square){
        this.moveableSquares.add(square);
    }
}
