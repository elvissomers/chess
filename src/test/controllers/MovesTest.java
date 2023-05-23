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
}