package main.classes.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.movement.MovementFinder;
import main.classes.structures.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    private Coordinate position;

    private BoardMap board;

    // TODO: Instead of having the Game & Team attribute, Piece should have a Player
    // TODO: attribute. Then we can get the game from the player, and also the Team
    // TODO: from the player.

    private Game game;

    private Team team;

    private Player player;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private Set<MovementType> moveRules = new HashSet<>();

    private MovementFinder moveFinder = new MovementFinder();

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

    public void newSetMovableSquares(){
        if (moveRules.contains(MovementType.HORIZONTAL))
            moveFinder.setHorizontalMoves(this, board);
        if (moveRules.contains(MovementType.VERTICAL))
            moveFinder.setVerticalMoves(this, board);
        if (moveRules.contains(MovementType.DIAGONAL))
            moveFinder.setDiagonalMoves(this, board);
        if (moveRules.contains(MovementType.LSHAPED))
            moveFinder.setLShapedMoves(this, board);
        if (moveRules.contains(MovementType.PAWN))
            moveFinder.setPawnMoves(this, board);
        if (moveRules.contains(MovementType.KING)) {
            moveFinder.setKingBasicMoves(this, board);
            moveFinder.setKingCastlingMoves(this, board);
        }
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }

    public Player getPlayer() {
        return player;
    }
}
