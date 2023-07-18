package com.ordina.nl.chess.service;

import com.ordina.nl.chess.entity.pieces.Piece;
import com.ordina.nl.chess.enums.MovementType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PieceService {

    public void setMovementTypesForPiece(Piece piece) {
        List<MovementType> movementTypes = piece.getMovementTypes();
        switch(piece.getPieceType()) {
            case KNIGHT -> movementTypes.add(MovementType.LSHAPED);
            case BISHOP -> movementTypes.add(MovementType.DIAGONAL);
            case KING -> movementTypes.add(MovementType.KING);
            case PAWN -> movementTypes.add(MovementType.PAWN);
            case ROOK -> {
                movementTypes.add(MovementType.HORIZONTAL);
                movementTypes.add(MovementType.VERTICAL);
            }
            case QUEEN -> {
                movementTypes.add(MovementType.DIAGONAL);
                movementTypes.add(MovementType.HORIZONTAL);
                movementTypes.add(MovementType.VERTICAL);
            }
        }
    }
}
