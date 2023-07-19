package com.ordina.nl.chess.data.dto.mapper;

import com.ordina.nl.chess.data.dto.PieceDto;
import com.ordina.nl.chess.entity.pieces.Piece;

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
