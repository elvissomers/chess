package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.MoveDto;
import com.practice.project.chess.repository.entity.Move;
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
                .takenPieceId(move.getTakenPiece().getId())
                .castleType(move.getCastleType())
                .promotedTo(move.getPromotedTo())
                .build();
    }
}