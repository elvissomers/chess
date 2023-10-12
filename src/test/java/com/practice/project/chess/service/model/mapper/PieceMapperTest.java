package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.model.pieces.*;
import com.practice.project.chess.service.structures.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PieceMapperTest {

    @Autowired
    private PieceMapper pieceMapper;

    @Test
    void daoAttributesCopied() {
        PieceDao testDao = new PieceDao();

        boolean hasMoved = false;
        Team team = Team.WHITE;
        PieceType bishop = PieceType.BISHOP;
        int verticalPosition = 0;
        int horizontalPosition = 2;

        testDao.setHasMoved(hasMoved);
        testDao.setPieceType(bishop);
        testDao.setVerticalPosition(verticalPosition);
        testDao.setHorizontalPosition(horizontalPosition);
        testDao.setTeam(team);

        Piece result = pieceMapper.daoToPiece(testDao);

        assertSame(result.getPieceType(), bishop);
        assertSame(result.getTeam(), team);
        assertSame(result.getHorizontalPosition(), horizontalPosition);
        assertSame(result.getVerticalPosition(), verticalPosition);

        assertEquals(result.getCoordinate(), new Coordinate(horizontalPosition, verticalPosition));
    }

    @Test
    void daoToPawn() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.PAWN);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof Pawn);
        assertSame(result.getPieceType(), PieceType.PAWN);
    }

    @Test
    void daoToKnight() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.KNIGHT);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof Knight);
        assertSame(result.getPieceType(), PieceType.KNIGHT);
    }

    @Test
    void daoToBishop() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.BISHOP);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof Bishop);
        assertSame(result.getPieceType(), PieceType.BISHOP);
    }

    @Test
    void daoToRook() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.ROOK);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof Rook);
        assertSame(result.getPieceType(), PieceType.ROOK);
    }

    @Test
    void daoToQueen() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.QUEEN);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof Queen);
        assertSame(result.getPieceType(), PieceType.QUEEN);
    }

    @Test
    void daoToKing() {
        PieceDao pawnDao = new PieceDao();
        pawnDao.setPieceType(PieceType.KING);

        Piece result = pieceMapper.daoToPiece(pawnDao);

        assertTrue(result instanceof King);
        assertSame(result.getPieceType(), PieceType.KING);
    }
}