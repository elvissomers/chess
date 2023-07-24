package com.practice.project.chess.data.dto.mapper;

import com.practice.project.chess.data.dto.MoveDto;
import com.practice.project.chess.entity.Move;
import org.springframework.stereotype.Component;

@Component
public class MoveDtoMapper {

    public MoveDto MoveToMoveDto(Move move) {
        return MoveDto.builder()
                .id(move.getId())
                .pieceId(move.getPiece().getId())
                .number(move.getNumber())
                .horizontalFrom(move.getHorizontalFrom())
                .verticalFrom(move.getVerticalFrom())
                .horizontalTo(move.getHorizontalTo())
                .verticalTo(move.getVerticalTo())
                .takenPiece(move.getTakenPiece() != null)
                .castleType(move.getCastleType())
                .build();
    }
}
