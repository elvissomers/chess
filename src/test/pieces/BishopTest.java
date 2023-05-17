package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }

    @Test
    public void testBishopStartPos() {
        Bishop bishop = (Bishop) startBoard.getSquareByPos(2, 0).getPiece();
        bishop.setMovableSquares();
        assertTrue(bishop.getMovableSquares().isEmpty(), "Bishop should not move at start position");
    }

    @Test
    public void testBishopPawnRemoved() {
        startBoard.getSquareByPos(3, 1).setPiece(null); // Removing the pawn at d2
        startBoard.getSquareByPos(4, 1).setPiece(null); // Removing the pawn at e2
        Bishop bishop = (Bishop) startBoard.getSquareByPos(2, 0).getPiece();
        bishop.setMovableSquares();

        List<Square> expectedSquares = new ArrayList<>();
        for (int i = 1; i < 6; i++){
             expectedSquares.add(startBoard.getSquareByPos(i+2, i));
        }
        assertTrue(bishop.getMovableSquares().containsAll(expectedSquares), "Bishop should move to squares (3,1) and (4,2) when pawns removed");
    }
}