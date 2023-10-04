package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.service.model.pieces.Piece;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
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
