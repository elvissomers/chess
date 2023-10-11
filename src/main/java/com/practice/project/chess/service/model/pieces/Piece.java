package com.practice.project.chess.service.model.pieces;

import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.repository.enums.PieceType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public abstract class Piece {

    private long id;

    private int horizontalPosition;
    private int verticalPosition;

    private Coordinate coordinate;

    private Player player;

    private Team team;

    private PieceType pieceType;

    private List<Coordinate> movableSquares = new ArrayList<>();

    private List<Coordinate> legalMovableSquares = new ArrayList<>();

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

    public boolean isHasMoved() {
        return true;
    };

    public void addMovableSquare(Coordinate coordinate){
        this.movableSquares.add(coordinate);
    }
}
