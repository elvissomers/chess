package com.practice.project.chess.repository.dao;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.PieceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MoveDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Piece piece;

    @Column(nullable = false)
    private int horizontalFrom;

    @Column(nullable = false)
    private int verticalFrom;

    @Column(nullable = false)
    private int horizontalTo;

    @Column(nullable = false)
    private int verticalTo;

    @OneToOne
    private Piece takenPiece;

    @Enumerated(EnumType.STRING)
    private CastleType castleType;

    @Enumerated(EnumType.STRING)
    private PieceType promotedTo;
}
