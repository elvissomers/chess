package com.practice.project.chess.data.dto.mapper;

import com.practice.project.chess.data.dto.PieceDto;
import com.practice.project.chess.entity.pieces.Piece;

public class PieceDtoMapper {

    public PieceDto pieceToPieceDto(Piece piece) {
        return PieceDto.builder()
                .id(piece.getId())
                .playerId(piece.getPlayer().getId())
                .horizontalPosition(piece.getHorizontalPosition())
                .verticalPosition(piece.getVerticalPosition())
                .pieceType(piece.getPieceType())
                .build();
    }
}
