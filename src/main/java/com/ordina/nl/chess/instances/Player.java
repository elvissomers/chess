package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.movement.MoveMaker;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.repository.MoveRepository;
import com.ordina.nl.chess.structures.BoardMap;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.Team;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Game game;

    @ManyToMany
    @JoinTable(
            name = "player_moves",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "move_id"))
    private List<Move> moveHistory = new ArrayList<>();

    @Column(nullable = false)
    private Team team;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private Set<Piece> pieces = new HashSet<>();

    private Set<Coordinate> allAttackedSquares = new HashSet<>();
    
    private Set<Coordinate> allMovableSquares = new HashSet<>();

    @Autowired
    private MoveRepository moveRepository;

    public Player() {

    }

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

    public Set<Coordinate> getAllAttackedSquares() {
        return allAttackedSquares;
    }

    public Set<Coordinate> getAllMovableSquares() {
        return allMovableSquares;
    }

    public MoveMaker getMoveMaker() {
        return game.getMoveMaker();
    }

    public void setAllAttackedSquares(BoardMap board) {
        allAttackedSquares = new HashSet<>();
        for (Piece piece : this.pieces){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares(board);
                allAttackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares(board);
                allAttackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }

    public King getKing() {
        for (Piece piece : pieces) {
            if (piece instanceof King)
                return (King) piece;
        }
        // Note: This should never be used! A player should always have a King!
        // TODO: replace this by throws KingNotFoundException
        return new King(this);
    }

    public List<Move> getPlayerMovesInOrder() {
        List<Move> playerMoves = moveRepository.findByPlayers_IdContaining(id);
        return playerMoves.stream()
                .sorted(Comparator.comparingInt(Move::getNumber))
                .collect(Collectors.toList());
    }

    public Move getLastMove() {
        List<Move> playerMoves = moveRepository.findByPlayers_IdContaining(id);
        return playerMoves.stream()
                .max(Comparator.comparingInt(Move::getNumber))
                .orElse(null);
    }

    public List<Move> getLastNMoves(int n) {
        List<Move> playerMoves = moveRepository.findByPlayers_IdContaining(id);
        return playerMoves.stream()
                .sorted(Comparator.comparingInt(Move::getNumber).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

}