package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }

    @Test
    public void testRookStartPos() {
        Rook rook = (Rook) startBoard.getSquareByPos(0, 0).getPiece();
        rook.setMovableSquares();
        assertTrue(rook.getMovableSquares().isEmpty(), "Rook should not move at start position");
    }

    @Test
    public void testRookEmptyBoard() {
        startBoard.getSquareByPos(0, 1).setPiece(null);
        Rook rook = (Rook) startBoard.getSquareByPos(0, 0).getPiece();
        rook.setMovableSquares();

        List<Square> expectedSquares = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedSquares.add(startBoard.getSquareByPos(0, i));
        }

        assertTrue(rook.getMovableSquares().containsAll(expectedSquares), "Rook should move to all squares on its line on empty board");
    }
}