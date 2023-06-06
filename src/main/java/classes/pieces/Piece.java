package classes.pieces;

import classes.instances.Player;
import classes.structures.Coordinate;
import classes.structures.MovementType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    private Coordinate position;

    private Player player;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private List<Coordinate> legalMovableSquares = new ArrayList<>();

    private Set<MovementType> moveRules = new HashSet<>();

    protected Piece(Player player) {
        this.player = player;
    }

    protected Piece(Piece other) {
        if (other == null){
            throw new IllegalArgumentException("Cannot copy a null Piece");
        }
        this.player = other.getPlayer(); // Will be set in set method, overwriting this original player
        this.position = other.getPosition();
        this.moveRules = other.getMoveRules();
    }

    public List<Coordinate> getMovableSquares() {
        return movableSquares;
    }

    public List<Coordinate> getLegalMovableSquares() {
        return legalMovableSquares;
    }

    public Set<MovementType> getMoveRules() {
        return moveRules;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public abstract Piece copy();

    public void removePreviousMovableSquares(){
        /*
         * Every implementation of setMovableSquares should call this
         * method, so that we clean out all previous movable squares
         * before setting new ones.
         */
        movableSquares = new ArrayList<>();
        legalMovableSquares = new ArrayList<>();
    }

    public void setMovableSquares(){
        removePreviousMovableSquares();
        for (MovementType moveRule : moveRules){
            moveRule.setMoves(this, player.getGame().getBoard(), player.getGame().getMoveFinder());
        }
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }

    public Player getPlayer() {
        return player;
    }

    // TODO: could use final instead, with two-argument copy constructor
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMoveRules(Set<MovementType> moveRules) {
        this.moveRules = moveRules;
    }
}
