package main.classes.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private Square square;

    private Board board;

    private Game game;

    public enum Team {
        WHITE, BLACK
    }

    private Team team;

    private List<Square> movableSquares = new ArrayList<>();

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Piece(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Square getSquare() {
        return square;
    }

    /*
     * set square method also updates the square from which the
     * piece was moved, and the square the pieces was moved to
     */
    public void setSquare(Square square) {
        if (this.square != null && this.square.getPiece() != null) {
            this.square.setPiece(null);
        }
        this.square = square;
        this.square.setPiece(this);
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

    public List<Square> getMovableSquares() {
        return movableSquares;
    }

    public abstract void setMovableSquares();

    public void addMovableSquare(Square square){
        this.movableSquares.add(square);
    }
}
