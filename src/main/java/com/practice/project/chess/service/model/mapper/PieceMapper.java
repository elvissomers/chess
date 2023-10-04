package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.service.model.pieces.*;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PieceMapper {

    public Piece daoToPiece(PieceDao dao) {
        Piece piece = null;
        switch(dao.getPieceType()) {
            case KING -> piece = mapToKing(dao);
            case ROOK -> piece = mapToRook(dao);
            case BISHOP -> piece = mapToBishop();
            case KNIGHT -> piece = mapToKnight();
            case PAWN -> piece = mapToPawn();
            case QUEEN -> piece = mapToQueen();
        }
        piece.setCoordinate(new Coordinate(dao.getHorizontalPosition(), dao.getVerticalPosition()));
        piece.setTeam(dao.getTeam());
        piece.setId(dao.getId());
        return piece;
    }

    private Piece mapToKing(PieceDao dao) {
        return King.builder().hasMoved(dao.isHasMoved()).build();
    }

    private Piece mapToRook(PieceDao dao) {
        return Rook.builder().hasMoved(dao.isHasMoved()).build();
    }

    private Piece mapToPawn() {
        return new Pawn();
    }

    private Piece mapToBishop() {
        return new Bishop();
    }

    private Piece mapToKnight() {
        return new Knight();
    }

    private Piece mapToQueen() {
        return new Queen();
    }
}
