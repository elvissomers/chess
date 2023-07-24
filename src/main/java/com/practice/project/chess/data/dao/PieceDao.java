package com.practice.project.chess.data.dao;

import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.PieceType;
import jakarta.persistence.*;


public class PieceDao {
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

    @Column
    private PieceType pieceType;

    public long getId() {
        return id;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public Player getPlayer() {
        return player;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
