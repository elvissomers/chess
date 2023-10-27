package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Bishop;
import com.practice.project.chess.service.model.pieces.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoveMapperTest {

    @Mock
    private PieceMapper pieceMapper;

    @InjectMocks
    private MoveMapper moveMapper;


    @Test
    void daoAttributesShouldBeCopied() {
        int verticalFrom = 0;
        int horizontalFrom = 2;
        int verticalTo = 1;
        int horizontalTo = 3;
        PieceDao piece = new PieceDao();
        piece.setPieceType(PieceType.BISHOP);
        PieceDao takenPiece = new PieceDao();
        takenPiece.setPieceType(PieceType.PAWN);

        Mockito.when(pieceMapper.daoToPiece(Mockito.any(PieceDao.class))).thenReturn(new Bishop());

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
        assertSame(result.getHorizontalTo(), horizontalTo);
        assertSame(result.getVerticalFrom(), verticalFrom);
        assertSame(result.getHorizontalFrom(), horizontalFrom);

        assertNull(result.getPromotedTo());
        assertNull(result.getCastleType());
    }

    @Test
    void nullDaoShouldReturnNull() {
        Move result = moveMapper.daoToMove(null);
        assertNull(result);
    }
}