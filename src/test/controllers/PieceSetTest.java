package test.controllers;

import main.classes.controllers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceSetTest {

    private Game game;
    @BeforeEach
    public void setUp() {
        game = new Game();
        game.getWhitePlayer().movePiece(game.getBoard().getSquareByPos(4,1).getPiece(),
                game.getBoard().getSquareByPos(4,3));
        game.getBlackPlayer().movePiece(game.getBoard().getSquareByPos(5,6).getPiece(),
                game.getBoard().getSquareByPos(5,4));
        game.getWhitePlayer().movePiece(game.getBoard().getSquareByPos(3,0).getPiece(),
                game.getBoard().getSquareByPos(7,4));
        //Qh5+, black is now in check!
        game.getWhitePlayer().getPieceSet().setAllMovableSquares();
        game.getWhitePlayer().getPieceSet().setAllAttackedSquares();
        game.getBlackPlayer().getPieceSet().setAllMovableSquares();
    }

    @Test
    void setAllMovableSquaresKingInCheckShouldPreventMovesNotResolvingCheck() {
        assertTrue(game.getBoard().getSquareByPos(0,6).getPiece().getMovableSquares().isEmpty());
        assertTrue(game.getBoard().getSquareByPos(1,6).getPiece().getMovableSquares().isEmpty());
        assertTrue(game.getBoard().getSquareByPos(1,7).getPiece().getMovableSquares().isEmpty());
    }

    @Test
    void setAllMovableSquaresCheckShouldNotPreventCheckingPlayerFromMoving(){
        assertFalse(game.getBoard().getSquareByPos(0,1).getPiece().getMovableSquares().isEmpty());
        assertFalse(game.getBoard().getSquareByPos(1,0).getPiece().getMovableSquares().isEmpty());
        assertFalse(game.getBoard().getSquareByPos(1,1).getPiece().getMovableSquares().isEmpty());
    }

    @Test
    void setAllMovableSquaresCheckPreventingMovesShouldBeAllowed(){
        assertEquals(1, game.getBoard().getSquareByPos(6,6).getPiece().getMovableSquares().size());
        assertEquals("g6", game.getBoard().getSquareByPos(6,6).getPiece().getMovableSquares()
                .get(0).toString());
    }
}