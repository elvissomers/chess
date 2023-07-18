package com.ordina.nl.chess.data.dto.mapper;

import com.ordina.nl.chess.data.dto.PieceDto;
import com.ordina.nl.chess.entity.pieces.Piece;

public class PieceDtoMapper {

    public PieceDto pieceToPieceDto(Piece piece) {
        return PieceDto.builder()
                .id(piece.getId())
                .pieceType(piece.getPieceType())
                .build();
    }
}
