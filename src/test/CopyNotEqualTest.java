package test;

import main.java.classes.instances.Game;

import main.java.classes.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CopyNotEqualTest {

    private Game game;
    private Game copyGame;

    @BeforeEach
    public void setup() {
        game = new Game();
        copyGame = new Game(game);
    }

    @Test
    public void newGameTestA1CopyNotEqual() {
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        Piece a1PieceCopy = copyGame.getBoard().getPieceByPos(0, 0);
        assertNotEquals(a1Piece, a1PieceCopy);
    }

    @Test
    public void newGameTestA1CopyRemovePiece_ShouldNotRemoveOriginalPiece() {
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        copyGame.getBoard().put(copyGame.getBoard().getCoordinateByPos(0,0), null);
        assertEquals(a1Piece, game.getBoard().getPieceByPos(0,0));
    }

    @Test
    public void newGameTestB1CopyNotEqual() {
        Piece b1Piece = game.getBoard().getPieceByPos(1, 0);
        Piece b1PieceCopy = copyGame.getBoard().getPieceByPos(1, 0);
        assertNotEquals(b1Piece, b1PieceCopy);
    }

    @Test
    public void newGameTestC1CopyNotEqual() {
        Piece c1Piece = game.getBoard().getPieceByPos(2, 0);
        Piece c1PieceCopy = copyGame.getBoard().getPieceByPos(2, 0);
        assertNotEquals(c1Piece, c1PieceCopy);
    }

    @Test
    public void newGameTestD1CopyNotEqual() {
        Piece d1Piece = game.getBoard().getPieceByPos(3, 0);
        Piece d1PieceCopy = copyGame.getBoard().getPieceByPos(3, 0);
        assertNotEquals(d1Piece, d1PieceCopy);
    }

    @Test
    public void newGameTestE1CopyNotEqual() {
        Piece e1Piece = game.getBoard().getPieceByPos(4, 0);
        Piece e1PieceCopy = copyGame.getBoard().getPieceByPos(4, 0);
        assertNotEquals(e1Piece, e1PieceCopy);
    }

    @Test
    public void newGameTestF1CopyNotEqual() {
        Piece f1Piece = game.getBoard().getPieceByPos(5, 0);
        Piece f1PieceCopy = copyGame.getBoard().getPieceByPos(5, 0);
        assertNotEquals(f1Piece, f1PieceCopy);
    }

    @Test
    public void newGameTestG1CopyNotEqual() {
        Piece g1Piece = game.getBoard().getPieceByPos(6, 0);
        Piece g1PieceCopy = copyGame.getBoard().getPieceByPos(6, 0);
        assertNotEquals(g1Piece, g1PieceCopy);
    }

    @Test
    public void newGameTestH1CopyNotEqual() {
        Piece h1Piece = game.getBoard().getPieceByPos(7, 0);
        Piece h1PieceCopy = copyGame.getBoard().getPieceByPos(7, 0);
        assertNotEquals(h1Piece, h1PieceCopy);
    }

    @Test
    public void newGameTestA2CopyNotEqual() {
        Piece a2Piece = game.getBoard().getPieceByPos(0, 1);
        Piece a2PieceCopy = copyGame.getBoard().getPieceByPos(0, 1);
        assertNotEquals(a2Piece, a2PieceCopy);
    }

    @Test
    public void newGameTestB2CopyNotEqual() {
        Piece b2Piece = game.getBoard().getPieceByPos(1, 1);
        Piece b2PieceCopy = copyGame.getBoard().getPieceByPos(1, 1);
        assertNotEquals(b2Piece, b2PieceCopy);
    }

    @Test
    public void newGameTestC2CopyNotEqual() {
        Piece c2Piece = game.getBoard().getPieceByPos(2, 1);
        Piece c2PieceCopy = copyGame.getBoard().getPieceByPos(2, 1);
        assertNotEquals(c2Piece, c2PieceCopy);
    }

    @Test
    public void newGameTestD2CopyNotEqual() {
        Piece d2Piece = game.getBoard().getPieceByPos(3, 1);
        Piece d2PieceCopy = copyGame.getBoard().getPieceByPos(3, 1);
        assertNotEquals(d2Piece, d2PieceCopy);
    }

    @Test
    public void newGameTestE2CopyNotEqual() {
        Piece e2Piece = game.getBoard().getPieceByPos(4, 1);
        Piece e2PieceCopy = copyGame.getBoard().getPieceByPos(4, 1);
        assertNotEquals(e2Piece, e2PieceCopy);
    }

    @Test
    public void newGameTestF2CopyNotEqual() {
        Piece f2Piece = game.getBoard().getPieceByPos(5, 1);
        Piece f2PieceCopy = copyGame.getBoard().getPieceByPos(5, 1);
        assertNotEquals(f2Piece, f2PieceCopy);
    }

    @Test
    public void newGameTestG2CopyNotEqual() {
        Piece g2Piece = game.getBoard().getPieceByPos(6, 1);
        Piece g2PieceCopy = copyGame.getBoard().getPieceByPos(6, 1);
        assertNotEquals(g2Piece, g2PieceCopy);
    }

    @Test
    public void newGameTestH2CopyNotEqual() {
        Piece h2Piece = game.getBoard().getPieceByPos(7, 1);
        Piece h2PieceCopy = copyGame.getBoard().getPieceByPos(7, 1);
        assertNotEquals(h2Piece, h2PieceCopy);
    }

    @Test
    public void newGameTestA7CopyNotEqual() {
        Piece a7Piece = game.getBoard().getPieceByPos(0, 6);
        Piece a7PieceCopy = copyGame.getBoard().getPieceByPos(0, 6);
        assertNotEquals(a7Piece, a7PieceCopy);
    }

    @Test
    public void newGameTestB7CopyNotEqual() {
        Piece b7Piece = game.getBoard().getPieceByPos(1, 6);
        Piece b7PieceCopy = copyGame.getBoard().getPieceByPos(1, 6);
        assertNotEquals(b7Piece, b7PieceCopy);
    }

    @Test
    public void newGameTestC7CopyNotEqual() {
        Piece c7Piece = game.getBoard().getPieceByPos(2, 6);
        Piece c7PieceCopy = copyGame.getBoard().getPieceByPos(2, 6);
        assertNotEquals(c7Piece, c7PieceCopy);
    }

    @Test
    public void newGameTestD7CopyNotEqual() {
        Piece d7Piece = game.getBoard().getPieceByPos(3, 6);
        Piece d7PieceCopy = copyGame.getBoard().getPieceByPos(3, 6);
        assertNotEquals(d7Piece, d7PieceCopy);
    }

    @Test
    public void newGameTestE7CopyNotEqual() {
        Piece e7Piece = game.getBoard().getPieceByPos(4, 6);
        Piece e7PieceCopy = copyGame.getBoard().getPieceByPos(4, 6);
        assertNotEquals(e7Piece, e7PieceCopy);
    }

    @Test
    public void newGameTestF7CopyNotEqual() {
        Piece f7Piece = game.getBoard().getPieceByPos(5, 6);
        Piece f7PieceCopy = copyGame.getBoard().getPieceByPos(5, 6);
        assertNotEquals(f7Piece, f7PieceCopy);
    }

    @Test
    public void newGameTestG7CopyNotEqual() {
        Piece g7Piece = game.getBoard().getPieceByPos(6, 6);
        Piece g7PieceCopy = copyGame.getBoard().getPieceByPos(6, 6);
        assertNotEquals(g7Piece, g7PieceCopy);
    }

    @Test
    public void newGameTestH7CopyNotEqual() {
        Piece h7Piece = game.getBoard().getPieceByPos(7, 6);
        Piece h7PieceCopy = copyGame.getBoard().getPieceByPos(7, 6);
        assertNotEquals(h7Piece, h7PieceCopy);
    }

    @Test
    public void newGameTestA8CopyNotEqual() {
        Piece a8Piece = game.getBoard().getPieceByPos(0, 7);
        Piece a8PieceCopy = copyGame.getBoard().getPieceByPos(0, 7);
        assertNotEquals(a8Piece, a8PieceCopy);
    }

    @Test
    public void newGameTestB8CopyNotEqual() {
        Piece b8Piece = game.getBoard().getPieceByPos(1, 7);
        Piece b8PieceCopy = copyGame.getBoard().getPieceByPos(1, 7);
        assertNotEquals(b8Piece, b8PieceCopy);
    }

    @Test
    public void newGameTestC8CopyNotEqual() {
        Piece c8Piece = game.getBoard().getPieceByPos(2, 7);
        Piece c8PieceCopy = copyGame.getBoard().getPieceByPos(2, 7);
        assertNotEquals(c8Piece, c8PieceCopy);
    }

    @Test
    public void newGameTestD8CopyNotEqual() {
        Piece d8Piece = game.getBoard().getPieceByPos(3, 7);
        Piece d8PieceCopy = copyGame.getBoard().getPieceByPos(3, 7);
        assertNotEquals(d8Piece, d8PieceCopy);
    }

    @Test
    public void newGameTestE8CopyNotEqual() {
        Piece e8Piece = game.getBoard().getPieceByPos(4, 7);
        Piece e8PieceCopy = copyGame.getBoard().getPieceByPos(4, 7);
        assertNotEquals(e8Piece, e8PieceCopy);
    }

    @Test
    public void newGameTestF8CopyNotEqual() {
        Piece f8Piece = game.getBoard().getPieceByPos(5, 7);
        Piece f8PieceCopy = copyGame.getBoard().getPieceByPos(5, 7);
        assertNotEquals(f8Piece, f8PieceCopy);
    }

    @Test
    public void newGameTestG8CopyNotEqual() {
        Piece g8Piece = game.getBoard().getPieceByPos(6, 7);
        Piece g8PieceCopy = copyGame.getBoard().getPieceByPos(6, 7);
        assertNotEquals(g8Piece, g8PieceCopy);
    }

    @Test
    public void newGameTestH8CopyNotEqual() {
        Piece h8Piece = game.getBoard().getPieceByPos(7, 7);
        Piece h8PieceCopy = copyGame.getBoard().getPieceByPos(7, 7);
        assertNotEquals(h8Piece, h8PieceCopy);
    }
}

