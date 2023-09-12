package com.practice.project.chess.entity;

import com.practice.project.chess.enums.CastleType;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.entity.pieces.Piece;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // TODO: moet koppeltabel worden tussen player en moves; could create PlayerMoveNumber object or sth
//    private int number;

    @Column(nullable = false)
    private Piece piece;

    @Column(nullable = false)
    private int horizontalFrom;

    @Column(nullable = false)
    private int verticalFrom;

    @Column(nullable = false)
    private int horizontalTo;

    @Column(nullable = false)
    private int verticalTo;

    private boolean takenPiece;
    private CastleType castleType;
    private PieceType promotedTo;

    @Override
    public String toString(){
        if (castleType == CastleType.SHORT)
            return "0-0";
        else if (castleType == CastleType.LONG)
            return "0-0-0";

        String moveChar = (takenPiece) ? "x" : "-";
        Coordinate squareFrom = new Coordinate(horizontalFrom, verticalFrom);
        Coordinate squareTo = new Coordinate(horizontalTo, verticalTo);
        String promotedString = "=" + promotedTo.toString(); // TODO: toString for PieceType
        return piece.toString() + squareFrom + moveChar + squareTo + promotedString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Move move = (Move) obj;

        return (Objects.equals(piece, move.piece) && horizontalFrom == move.horizontalFrom &&
                horizontalTo == move.horizontalTo && verticalFrom == move.verticalFrom &&
                verticalTo == move.verticalTo);
        // TODO: number?
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, horizontalFrom, horizontalTo, verticalFrom, verticalTo);
    }
}
