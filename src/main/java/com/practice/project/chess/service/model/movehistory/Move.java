package com.practice.project.chess.service.model.movehistory;

import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.model.pieces.Piece;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Move {

    private long id;

    private Piece piece;

    private int horizontalFrom;

    private int verticalFrom;

    private int horizontalTo;

    private int verticalTo;

    private Piece takenPiece;

    private CastleType castleType;

    private PieceType promotedTo;

    @Override
    public String toString(){
        if (castleType == CastleType.SHORT)
            return "0-0";
        else if (castleType == CastleType.LONG)
            return "0-0-0";

        String moveChar = (takenPiece ==null) ? "-" : "x";
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
                verticalTo == move.verticalTo && Objects.equals(takenPiece, move.getTakenPiece()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, horizontalFrom, horizontalTo, verticalFrom, verticalTo, takenPiece);
    }
}
