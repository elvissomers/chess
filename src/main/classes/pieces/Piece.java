package main.classes.pieces;

import main.classes.controllers.Player;
import main.classes.structures.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    private Coordinate position;

    private BoardMap board;

    private final Player player;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private Set<MovementType> moveRules = new HashSet<>();

    public Piece(Player player) {
        this.player = player;
    }

    // TODO: Piece copy constructor should be working properly
    // That means it should also copy null
    public Piece(Piece other) {
        if (other == null){
            throw new IllegalArgumentException("Cannot copy a null Piece");
        }

        this.player = other.getPlayer();
        this.position = other.getPosition();
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
            moveRule.setMoves(this, board, player.getGame().getMoveFinder());
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
