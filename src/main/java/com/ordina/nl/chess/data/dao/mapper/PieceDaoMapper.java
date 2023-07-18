package com.ordina.nl.chess.data.dao.mapper;

import com.ordina.nl.chess.data.dao.PieceDao;
import com.ordina.nl.chess.entity.pieces.*;

public class PieceDaoMapper {

    public Piece pieceDaoToPiece(PieceDao dao) {
        Piece piece = null;
        switch(dao.getPieceType()) {
            case KING -> piece = new King();
            case PAWN -> piece = new Pawn();
            case ROOK -> piece = new Rook();
            case QUEEN -> piece = new Queen();
            case BISHOP -> piece = new Bishop();
            case KNIGHT -> piece = new Knight();
        }

        piece.setPlayer(dao.getPlayer());
        piece.setVerticalPosition(dao.getVerticalPosition());
        piece.setHorizontalPosition(dao.getHorizontalPosition());
        piece.setHasMoved(dao.isHasMoved());
        piece.setPieceType(dao.getPieceType());

        return piece;
    }
}
