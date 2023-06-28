package com.ordina.nl.chess.pieces;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.movement.MoveFinder;
import com.ordina.nl.chess.structures.BoardMap;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.MovementType;
import com.ordina.nl.chess.structures.PieceType;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public abstract class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int horizontalPosition;

    @Column
    private int verticalPosition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private PieceType pieceType;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private List<Coordinate> legalMovableSquares = new ArrayList<>();

    private Set<MovementType> moveRules = new HashSet<>();

    @Autowired
    private MoveFinder moveFinder;

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
            moveRule.setMoves(this, board, moveFinder, player.getGame());
        }
    }

    public void setLegalMovableSquares() {
        legalMovableSquares = new ArrayList<>();
        moveFinder.pruneSelfCheckMovesForPiece(this, player.getGame());
    }

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }

    public Player getPlayer() {
        return player;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public void setMoveRules(Set<MovementType> moveRules) {
        this.moveRules = moveRules;
    }
}
