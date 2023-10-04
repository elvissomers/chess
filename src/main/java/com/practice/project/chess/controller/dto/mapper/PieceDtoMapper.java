package com.practice.project.chess.controller.dto.mapper;

import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.service.model.pieces.Piece;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PieceDtoMapper {

    public PieceDto pieceToPieceDto(PieceDao piece) {
        return PieceDto.builder()
                .horizontalPosition(piece.getHorizontalPosition())
                .verticalPosition(piece.getVerticalPosition())
                .pieceType(piece.getPieceType())
                .team(piece.getTeam())
                .build();
    }
}
