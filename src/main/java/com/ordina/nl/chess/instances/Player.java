package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.movement.MoveMaker;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private final Game game;

    @ManyToMany
    @JoinTable(
            name = "player_moves",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "move_id"))
    private List<Move> moveHistory = new ArrayList<>();

    @Column(nullable = false)
    private final Team team;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private Set<Piece> pieces = new HashSet<>();

    private Set<Coordinate> allAttackedSquares = new HashSet<>();
    
    private Set<Coordinate> allMovableSquares = new HashSet<>();

    // TODO: just get it from pieces whenever needed
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

    // TODO: getKing without attribute here
    public King getKingNew() {
        for (Piece piece : pieces) {
            if (piece instanceof King)
                return (King) piece;
        }
        return null;
    }
}