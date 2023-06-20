package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.movement.MoveMaker;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {

    private final Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private Set<Piece> pieces = new HashSet<>();

    private Set<Coordinate> allAttackedSquares = new HashSet<>();
    
    private Set<Coordinate> allMovableSquares = new HashSet<>();

    private King king;

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

    public Set<Coordinate> getAllMovableSquares() {
        return allMovableSquares;
    }

    public MoveMaker getMoveMaker() {
        return game.getMoveMaker();
    }

    public void setKing(King king) {
        this.king = king;
    }

    /**
     * Also sets all movable squares for all the pieces
     */
    public void setAllAttackedAndMovableSquares() {
        allAttackedSquares = new HashSet<>();
        allMovableSquares = new HashSet<>();
        for (Piece piece : this.pieces){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares(game.getBoard());
                pawn.setMovableSquares();
                allMovableSquares.addAll(pawn.getMovableSquares());
                allAttackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares();
                allMovableSquares.addAll(piece.getMovableSquares());
                allAttackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }
}