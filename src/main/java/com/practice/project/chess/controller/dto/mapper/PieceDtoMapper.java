package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.repository.entity.pieces.Piece;

public class PieceDtoMapper {

    public PieceDto pieceToPieceDto(Piece piece) {
        return PieceDto.builder()
                .horizontalPosition(piece.getHorizontalPosition())
                .verticalPosition(piece.getVerticalPosition())
                .pieceType(piece.getPieceType())
                .team(piece.getPlayer().getTeam())
                .build();
    }
}
