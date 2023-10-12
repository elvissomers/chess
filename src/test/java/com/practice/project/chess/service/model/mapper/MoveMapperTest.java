package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.*;
import com.practice.project.chess.service.structures.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoveMapperTest {

    @Autowired
    private MoveMapper moveMapper;


    @Test
    void daoAttributesCopied() {
        // TODO null check in piecemapper (and movemapper i geuss)
        int verticalFrom = 0;
        int horizontalFrom = 2;
        int verticalTo = 1;
        int horizontalTo = 3;
        PieceDao piece = new PieceDao();
        piece.setPieceType(PieceType.BISHOP);
        PieceDao takenPiece = new PieceDao();
        takenPiece.setPieceType(PieceType.PAWN);

        MoveDao moveDao = MoveDao.builder()
                .verticalFrom(verticalFrom)
                .horizontalFrom(horizontalFrom)
                .verticalTo(verticalTo)
                .horizontalTo(horizontalTo)
                .piece(piece)
                .takenPiece(takenPiece)
                .build();

        Move result = moveMapper.daoToMove(moveDao);

        assertSame(result.getVerticalTo(), verticalTo);
    }
}