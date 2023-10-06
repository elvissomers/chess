package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.service.model.movehistory.Move;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MoveMapper {

    private final PieceMapper pieceMapper;

    public Move daoToMove(MoveDao dao) {
        return Move.builder()
                .id(dao.getId())
                .horizontalFrom(dao.getHorizontalFrom())
                .verticalFrom(dao.getVerticalFrom())
                .horizontalTo(dao.getHorizontalTo())
                .verticalTo(dao.getVerticalTo())
                .castleType(dao.getCastleType())
                .promotedTo(dao.getPromotedTo())
                .piece(pieceMapper.daoToPiece(dao.getPiece()))
                .takenPiece(pieceMapper.daoToPiece(dao.getTakenPiece()))
                .build();
    }
}
