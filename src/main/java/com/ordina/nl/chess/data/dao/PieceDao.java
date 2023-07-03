package com.ordina.nl.chess.data.dao;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.PieceType;
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

    @Transient
    private PieceType pieceType;

}
