package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }
    
    @Test
    public void testQueenStartPos() {
        Queen queen = (Queen) startBoard.getSquareByPos(3, 0).getPiece();
        queen.setMovableSquares();
        assertTrue(queen.getMovableSquares().isEmpty(), "Queen should not move at start position");
    }

    @Test
    public void testQueenPawnRemoved() {
        startBoard.getSquareByPos(3, 1).setPiece(null); // Removing the pawn at d2
        startBoard.getSquareByPos(4, 1).setPiece(null); // Removing the pawn at e2
        Queen queen = (Queen) startBoard.getSquareByPos(3, 0).getPiece();
        queen.setMovableSquares();

        List<Square> expectedSquares = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedSquares.add(startBoard.getSquareByPos(3, i)); // Vertical movement
        }
        for (int i = 1; i < 5; i++) {
            expectedSquares.add(startBoard.getSquareByPos(i+3, i)); // Diagonal movement
        }


        assertTrue(queen.getMovableSquares().containsAll(expectedSquares), "Queen should move to all squares on its line and diagonal when pawns removed");
    }
}