package com.practice.project.chess.repository.dao.pieces;

import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="pieces")
public class PieceDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int horizontalPosition;
    private int verticalPosition;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Enumerated(EnumType.STRING)
    private PieceType pieceType;

    private boolean hasMoved;
}