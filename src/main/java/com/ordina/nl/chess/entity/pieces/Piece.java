package com.ordina.nl.chess.entity.pieces;

import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
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
    // TODO: move back to king & rook
    private boolean hasMoved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    @Transient
    private PieceType pieceType;

    @Transient
    private List<Coordinate> movableSquares = new ArrayList<>();

    @Transient
    private List<Coordinate> legalMovableSquares = new ArrayList<>();

    @Transient
    private List<MovementType> movementTypes = new ArrayList<>();

    @Transient
    private List<Coordinate> attackedSquares = new ArrayList<>();

//    private MoveFinder moveFinder;

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
        this.moveRules = other.getMoveRules();
    }

    public abstract Piece copy();

    public void setCorrectMoveRules() {
        setMoveRules(getPieceType().getMovementTypes());
    };

    public void setMovableSquares(BoardMap board) {
        movableSquares = new ArrayList<>();
        for (MovementType movementType : movementTypes){
//            moveRule.setMoves(this, board, moveFinder, player.getGame());
            int b = 5;
        }
    }

    public void setLegalMovableSquares() {
        legalMovableSquares = new ArrayList<>();
//        moveFinder.pruneSelfCheckMovesForPiece(this, player.getGame());
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }
}
