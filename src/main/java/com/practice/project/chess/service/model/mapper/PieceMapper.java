package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.service.model.pieces.*;
import com.practice.project.chess.service.structures.Coordinate;
import org.springframework.stereotype.Component;

@Component
public class PieceMapper {

    public Piece daoToPiece(PieceDao dao) {
        if (dao == null)
            return null;

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
        // TODO: are these needed?
        piece.setPieceType(dao.getPieceType());
        piece.setHorizontalPosition(dao.getHorizontalPosition());
        piece.setVerticalPosition(dao.getVerticalPosition());
        return piece;
    }

    private Piece mapToKing(PieceDao dao) {
        King king = new King();
        king.setHasMoved(dao.isHasMoved());
        return king;
    }

    private Piece mapToRook(PieceDao dao) {
        Rook rook = new Rook();
        rook.setHasMoved(dao.isHasMoved());
        return rook;
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
