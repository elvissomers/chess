package main.classes.controllers;

import main.classes.game.Move;
import main.classes.movement.MoveMaker;
import main.classes.pieces.*;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {

    private final Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private Set<Piece> pieces;

    private Set<Coordinate> allAttackedSquares = new HashSet<>();

    private King king;

    private final MoveMaker moveMaker = new MoveMaker();

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Team getTeam() {
        return team;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public King getKing() {
        return king;
    }

    public Set<Coordinate> getAllAttackedSquares() {
        return allAttackedSquares;
    }

    public MoveMaker getMoveMaker() {
        return moveMaker;
    }

    public void setAllMovableSquares() {
        for (Piece piece : pieces) {
            piece.setMovableSquares();
        }
    }

    public void setAllAttackedSquares() {
        for (Piece piece : this.pieces){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares(game.getBoard());
                allAttackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares();
                allAttackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }
}