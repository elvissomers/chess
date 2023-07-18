package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.BoardMap;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="pieces")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "piece_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int horizontalPosition;

    @Column
    private int verticalPosition;

    @Column(nullable = true)
    private boolean hasMoved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    @Transient
    private PieceType pieceTyperino;

    @Transient
    private List<Coordinate> movableSquares = new ArrayList<>();

    @Transient
    private List<Coordinate> legalMovableSquares = new ArrayList<>();

    @Transient
    private Set<MovementType> moveRules = new HashSet<>();

    @Transient
    private Set<Coordinate> attackedSquares = new HashSet<>();

//    private MoveFinder moveFinder;

    protected Piece(Player player) {
        this.player = player;
    }

    protected Piece() {

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

    public List<Coordinate> getMovableSquares() {
        return movableSquares;
    }

    public List<Coordinate> getLegalMovableSquares() {
        return legalMovableSquares;
    }

    public Set<MovementType> getMoveRules() {
        return moveRules;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public abstract Piece copy();

    public abstract void setCorrectPieceType();

    public void setCorrectMoveRules() {
        setMoveRules(getPieceType().getMovementTypes());
    };

    public void setMovableSquares(BoardMap board) {
        movableSquares = new ArrayList<>();
        for (MovementType moveRule : moveRules){
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

    public Player getPlayer() {
        return player;
    }

    public PieceType getPieceType() {
        return pieceTyperino;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }



    public void setPieceType(PieceType pieceType) {
        this.pieceTyperino = pieceType;
    }

    public void setMoveRules(Set<MovementType> moveRules) {
        this.moveRules = moveRules;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Set<Coordinate> getAttackedSquares() {
        return attackedSquares;
    }
}
