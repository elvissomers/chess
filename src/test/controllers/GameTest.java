package test.controllers;

import main.classes.controllers.Game;
import main.classes.pieces.King;
import main.classes.pieces.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void newGameTestKing(){
        Game game = new Game();

        Piece e1Piece = game.getBoard().getPieceByPos(4,0);
        assertTrue(e1Piece instanceof King);
    }

}