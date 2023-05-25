package main.classes.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    private Set<MovementType> movementTypes;

    private Square square;

    private Board board;

    // TODO: Instead of having the Game & Team attribute, Piece should have a Player
    // TODO: attribute. Then we can get the game from the player, and also the Team
    // TODO: from the player.

    private Game game;

    private Team team;

    private List<Square> movableSquares = new ArrayList<>();

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    // TODO: set square in constructor
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

    public void removePreviousMovableSquares(){
        /*
         * Every implementation of setMovableSquares should call this
         * method, so that we clean out all previous movable squares
         * before setting new ones.
         */
        movableSquares = new ArrayList<>();
    }

    public void addMovableSquare(Square square){
        this.movableSquares.add(square);
    }
}
