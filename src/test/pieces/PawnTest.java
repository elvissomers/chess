package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.Pawn;
import main.classes.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }

    @Test
    public void testPawnStartPos() {
        Pawn Pawn = (Pawn) startBoard.getSquareByPos(3, 1).getPiece();
        Pawn.setMovableSquares();

        List<Square> expectedSquares = Arrays.asList(startBoard.getSquareByPos(3, 2), startBoard.getSquareByPos(3, 3));

        assertTrue(Pawn.getMovableSquares().containsAll(expectedSquares),
                "Pawn should move to squares (3,2) and (3,3) at start position");
        assertTrue(expectedSquares.containsAll(Pawn.getMovableSquares()),
                "Pawn should move to no other squares than (3,2) and (3,3) at start position");
    }

    @Test
    public void testPawnStartPosWithTake() {
        Pawn Pawn = (Pawn) startBoard.getSquareByPos(3, 1).getPiece();

        Pawn enemyPawn = new Pawn(Piece.Team.BLACK);
        startBoard.getSquareByPos(4,2).setPiece(enemyPawn);
        Pawn.setMovableSquares();

        List<Square> expectedSquares = Arrays.asList(startBoard.getSquareByPos(3, 2),
                startBoard.getSquareByPos(3, 3), startBoard.getSquareByPos(4, 2));

        assertTrue(Pawn.getMovableSquares().containsAll(expectedSquares),
                "Pawn should move to squares (3,2), (4,2) and (3,3) at start position");
        assertTrue(expectedSquares.containsAll(Pawn.getMovableSquares()),
                "Pawn should move to no other squares than (3,2), (4,2) and (3,3) at start position");
    }
}