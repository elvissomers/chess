package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.repository.PieceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PieceService {

    private final PawnService pawnService;
    private final KnightService knightService;
    private final BishopService bishopService;
    private final RookService rookService;
    private final QueenService queenService;
    private final KingService kingService;

    private final PieceRepository pieceRepository;

    public Piece createPiece(PieceType pieceType, Player player, int horizontalPosition, int verticalPosition) {
        Piece piece = null;
        switch(pieceType) {
            case KING -> piece = new King();
            case PAWN -> piece = new Pawn();
            case ROOK -> piece = new Rook();
            case QUEEN -> piece = new Queen();
            case BISHOP -> piece = new Bishop();
            case KNIGHT -> piece = new Knight();
        }

        piece.setHorizontalPosition(horizontalPosition);
        piece.setVerticalPosition(verticalPosition);
        piece.setPlayer(player);

        return pieceRepository.save(piece);
    }

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
