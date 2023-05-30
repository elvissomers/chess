package main.classes.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.Coordinate;
import main.classes.structures.PieceType;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private Coordinate position;

    private Board board;

    // TODO: Instead of having the Game & Team attribute, Piece should have a Player
    // TODO: attribute. Then we can get the game from the player, and also the Team
    // TODO: from the player.

    private Game game;

    private Team team;

    private List<Coordinate> movableSquares = new ArrayList<>();

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    // TODO: make every creation of Piece use this constructor
    // TODO: make logic depend on player instead of game
    public Piece(Player player){
        this.game = player.getGame();
        this.team = player.getTeam();
    }

    public Piece(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Piece(Piece other) {
        this.team = other.getTeam();
        // Game & Square will be set separately
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

    public List<Coordinate> getMovableSquares() {
        return movableSquares;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public abstract void setMovableSquares();

    public abstract Piece copy();

    public void removePreviousMovableSquares(){
        /*
         * Every implementation of setMovableSquares should call this
         * method, so that we clean out all previous movable squares
         * before setting new ones.
         */
        movableSquares = new ArrayList<>();
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }
}
