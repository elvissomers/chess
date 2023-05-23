package test.controllers;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.controllers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.classes.structures.Team;

import java.util.List;

public class MovesTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.getWhitePlayer().movePiece(game.getBoard().getSquareByPos(4,1).getPiece(),
                game.getBoard().getSquareByPos(4,3));
    }

    @Test
    void testMoveStringGame(){
        Move e2e4 = game.getMoveHistory().get(0);
        assertEquals("e2-e4",e2e4.toString());
    }

    @Test
    void testMoveStringPlayer(){
        Move e2e4 = game.getWhitePlayer().getMoveHistory().get(0);
        assertEquals("e2-e4",e2e4.toString());
    }
    @Test
    void testPawnMovableSquares(){
        game.getBoard().getSquareByPos(4,3).getPiece().setMovableSquares();
        List<Square> e4Movables = game.getBoard().getSquareByPos(4,3).getPiece().getMovableSquares();
        assertEquals(1, e4Movables.size());
        assertEquals("e5",e4Movables.get(0).toString());
    }

    @Test
    void testPawnMovableSquaresWithAttack(){
        game.getBlackPlayer().movePiece(game.getBoard().getSquareByPos(3,6).getPiece(),
                game.getBoard().getSquareByPos(3,4));
        game.getBoard().getSquareByPos(4,3).getPiece().setMovableSquares();
        List<Square> e4Movables = game.getBoard().getSquareByPos(4,3).getPiece().getMovableSquares();
        assertEquals(2, e4Movables.size());
        // TODO: don't use indices here!
        assertEquals("e5",e4Movables.get(0).toString());
        assertEquals("d5",e4Movables.get(1).toString());
    }

    @Test
    void testPawnMovableSquaresWithEnPassant(){
        game.getBlackPlayer().movePiece(game.getBoard().getSquareByPos(3,6).getPiece(),
                game.getBoard().getSquareByPos(3,4));
        game.getWhitePlayer().movePiece(game.getBoard().getSquareByPos(4,3).getPiece(),
                game.getBoard().getSquareByPos(4,4));
        game.getBlackPlayer().movePiece(game.getBoard().getSquareByPos(5,6).getPiece(),
                game.getBoard().getSquareByPos(5,4));

        game.getBoard().getSquareByPos(4,4).getPiece().setMovableSquares();
        List<Square> e5Movables = game.getBoard().getSquareByPos(4,4).getPiece().getMovableSquares();
        assertEquals(2, e5Movables.size());
        // TODO: don't use indices here!
        assertEquals("e6",e5Movables.get(0).toString());
        assertEquals("f6",e5Movables.get(1).toString());
    }
}