package test.board;

import main.classes.board.Board;
import main.classes.controllers.Game;
import main.classes.pieces.Queen;
import main.classes.structures.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testBoardDeepCopy_ChangingCopyShouldNotChangeOriginal() {
        Board board = game.getBoard();

        Board copyBoard = new Board(board);
        Queen extraQueen = new Queen(game, Team.WHITE);
        copyBoard.getSquareByPos(3,2).setPiece(extraQueen);

        assertNull(board.getSquareByPos(3,2).getPiece());

    }

}