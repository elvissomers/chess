package test.pieces;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    Board startBoard;

    Board emptyBoard;
    @BeforeEach
    public void setStartAndEmptyBoard(){
        Game game = new Game();
        startBoard = game.getBoard();
        emptyBoard = new Board(8, 8);
    }

    @Test
    public void testKnightStartPos() {
        Knight knight = (Knight) startBoard.getSquareByPos(1, 0).getPiece();
        knight.setMovableSquares();

        List<Square> expectedSquares = Arrays.asList(startBoard.getSquareByPos(0, 2), startBoard.getSquareByPos(2, 2));

        assertTrue(knight.getMovableSquares().containsAll(expectedSquares),
                "Knight should move to squares (0,2) and (2,2) at start position");
        assertTrue(expectedSquares.containsAll(knight.getMovableSquares()),
                "Knight should move to no other squares than (0,2) and (2,2) at start position");
    }
}