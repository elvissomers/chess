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
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
            // Question: could we add a separate attribute, say "number" to the join table here?
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "move_id"))

    private List<Move> moveHistory = new ArrayList<>();
    // Question: do we even need a moveHistory attribute in player, if we can instead create a separate player object?

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
}