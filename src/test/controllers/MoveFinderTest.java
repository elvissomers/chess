package test.controllers;

import main.classes.controllers.Game;
import main.classes.game.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveFinderTest {

    private Game game;
    @BeforeEach
    public void setUp() {
        game = new Game();
        game.update();
        Move e2e4 = game.getMoveMaker().getMove(game, game.getBoard().getPieceByPos(4,1),
                game.getBoard().getCoordinateByPos(4,3));
        game.getMoveMaker().makeMove(e2e4, game);
        game.update();
        Move f7f5 = game.getMoveMaker().getMove(game, game.getBoard().getPieceByPos(5,6),
                game.getBoard().getCoordinateByPos(5,4));
        game.getMoveMaker().makeMove(f7f5, game);
        game.update();
        Move Qh5 = game.getMoveMaker().getMove(game, game.getBoard().getPieceByPos(3,0),
                game.getBoard().getCoordinateByPos(7,4));
        game.getMoveMaker().makeMove(Qh5, game);
        game.update();
        //Qh5+, black is now in check!
    }

    @Test
    void setAllMovableSquaresKingInCheckShouldPreventMovesNotResolvingCheck() {
        assertTrue(game.getBoard().getPieceByPos(0,6).getMovableSquares().isEmpty());
        assertTrue(game.getBoard().getPieceByPos(1,6).getMovableSquares().isEmpty());
        assertTrue(game.getBoard().getPieceByPos(1,7).getMovableSquares().isEmpty());
    }

    @Test
    void setAllMovableSquaresCheckShouldNotPreventCheckingPlayerFromMoving(){
        assertFalse(game.getBoard().getPieceByPos(0,1).getMovableSquares().isEmpty());
        assertFalse(game.getBoard().getPieceByPos(1,0).getMovableSquares().isEmpty());
        assertFalse(game.getBoard().getPieceByPos(1,1).getMovableSquares().isEmpty());
    }

    @Test
    void setAllMovableSquaresCheckPreventingMovesShouldBeAllowed(){
        assertEquals(1, game.getBoard().getPieceByPos(6,6).getMovableSquares().size());
        assertEquals("g6", game.getBoard().getPieceByPos(6,6).getMovableSquares()
                .get(0).toString());
    }
}