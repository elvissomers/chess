package com.practice.project.chess.entity;

import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.repository.MoveRepository;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.enums.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Team team;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<Piece> pieces = new ArrayList<>();

    private Set<Coordinate> allAttackedSquares = new HashSet<>();

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private MoveOptionService moveFinder;

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
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