package com.ordina.nl.chess.data.dto.mapper;

import com.ordina.nl.chess.data.dto.MoveDto;
import com.ordina.nl.chess.entity.Move;
import org.springframework.stereotype.Component;

@Component
public class MoveDtoMapper {

    public MoveDto MoveToMoveDto(Move move) {
        return MoveDto.builder()
                .id(move.getId())
                .pieceId(move.getPiece().getId())
                .horizontalFrom(move.getHorizontalFrom())
                .verticalFrom(move.getVerticalFrom())
                .horizontalTo(move.getHorizontalTo())
                .verticalTo(move.getVerticalTo())
                .takenPiece(move.getTakenPiece() != null)
                .castleType(move.getCastleType())
                .build();
    }
}
