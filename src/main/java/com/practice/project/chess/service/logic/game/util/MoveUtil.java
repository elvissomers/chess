package com.practice.project.chess.service.logic.game.util;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.model.movehistory.Move;

public final class MoveUtil {

    private MoveUtil(){
    }

    public static void updateSpecialMove(MoveDao move, CastleType castleType, PieceType promotedTo) {
        if (castleType != null)
            move.setCastleType(castleType);
        if (promotedTo != null)
            move.setPromotedTo(promotedTo);
    }

    public static boolean pawnMovedDiagonally(MoveDao move) {
        return (move.getPiece().getPieceType() == PieceType.PAWN &&
                move.getHorizontalFrom() != move.getHorizontalTo());
    }
}
