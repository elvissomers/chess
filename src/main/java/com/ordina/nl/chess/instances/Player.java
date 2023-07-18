package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.exception.ElementNotFoundException;
import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.service.MoveFinder;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.repository.MoveRepository;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import com.ordina.nl.chess.enums.Team;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Entity
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
    private List<Piece> pieces = new ArrayList<>();

    private Set<Coordinate> allAttackedSquares = new HashSet<>();

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private MoveFinder moveFinder;

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

    public List<Piece> getPieces() {
        return pieces;
    }

    public Set<Coordinate> getAllAttackedSquares() {
        return allAttackedSquares;
    }

    //TODO : use canPlayerMove in game
    public boolean canMove() {
        BoardMap board = moveFinder.setBoardMap(game);
        for (Piece piece : pieces) {
            game.setMovableSquaresForPiece(piece, board);
            if (!piece.getLegalMovableSquares().isEmpty())
                return true;
        }
        return false;
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

    public King getKing() throws ElementNotFoundException {
        for (Piece piece : pieces) {
            if (piece instanceof King)
                return (King) piece;
        }
        throw new ElementNotFoundException("Player's King not found!");
    }

    public int getNumberOfMoves() {
        return getLastMove().getNumber();
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