package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.King;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }

    @Test
    public void testKingStartPos() {
        King king = (King) startBoard.getSquareByPos(4, 0).getPiece();
        king.setMovableSquares();
        assertTrue(king.getMovableSquares().isEmpty(), "King should not move at start position");
    }

    @Test
    public void testKingPawnRemoved() {
        startBoard.getSquareByPos(3, 1).setPiece(null); // Removing the pawn at d2
        startBoard.getSquareByPos(4, 1).setPiece(null); // Removing the pawn at e2
        King king = (King) startBoard.getSquareByPos(4, 0).getPiece();
        king.setMovableSquares();

        List<Square> expectedSquares = Arrays.asList(startBoard.getSquareByPos(3, 1), startBoard.getSquareByPos(4, 1));

        assertTrue(king.getMovableSquares().containsAll(expectedSquares),
                "King should move to squares d2 and e2 with pawns removed");
        assertTrue(expectedSquares.containsAll(king.getMovableSquares()),
                "King should move to ONLY squares d2 and e2 with pawns removed");
    }
}