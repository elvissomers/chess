package com.practice.project.chess.service.model.pieces;

import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.repository.enums.PieceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="pieces")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "piece_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int horizontalPosition;
    private int verticalPosition;

    private Coordinate coordinate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    private Team team;

    @Transient
    private PieceType pieceType;

    @Transient
    private List<Coordinate> movableSquares = new ArrayList<>();

    @Transient
    private List<Coordinate> legalMovableSquares = new ArrayList<>();

    @Transient
    private List<Coordinate> attackedSquares = new ArrayList<>();

    protected Piece(Player player) {
        this.player = player;
    }

    protected Piece(Piece other) {
        if (other == null){
            throw new IllegalArgumentException("Cannot copy a null Piece");
        }
        this.player = other.getPlayer(); // Will be set in set method, overwriting this original player
        this.horizontalPosition = other.getHorizontalPosition();
        this.verticalPosition = other.getVerticalPosition();
        this.movableSquares = new ArrayList<>();
        this.legalMovableSquares = new ArrayList<>();
        this.attackedSquares = new ArrayList<>();
    }

    public abstract Piece copy();

    public abstract boolean isHasMoved();

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }
}
