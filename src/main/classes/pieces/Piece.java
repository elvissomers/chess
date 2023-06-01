package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.movement.MoveFinder;
import main.classes.structures.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    private Coordinate position;

    private BoardMap board;

    private Player player;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private Set<MovementType> moveRules = new HashSet<>();

    private MoveFinder moveFinder = new MoveFinder();

    // TODO: make every creation of Piece use this constructor
    // TODO: make logic depend on player instead of game
    public Piece(Player player) {
        this.player = player;
    }

    // TODO: Piece copy constructor should be working properly
    public Piece(Piece other) {
        this.player = other.getPlayer();
        // Game & Square will be set separately
    }

    public BoardMap getBoard() {
        return board;
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

    public abstract Piece copy();

    public void removePreviousMovableSquares(){
        /*
         * Every implementation of setMovableSquares should call this
         * method, so that we clean out all previous movable squares
         * before setting new ones.
         */
        movableSquares = new ArrayList<>();
    }

    public void setMovableSquares(){
        for (MovementType moveRule : moveRules){
            moveRule.setMoves(this, board, moveFinder);
        }
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }

    public Player getPlayer() {
        return player;
    }

    public void setMoveRules(Set<MovementType> moveRules) {
        this.moveRules = moveRules;
    }
}
