package test.controllers;

import main.classes.controllers.Game;
import main.classes.pieces.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartPositionTypeTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
    }

    @Test
    public void newGameTestA1HasRook() {
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        assertTrue(a1Piece instanceof Rook);
    }

    @Test
    public void newGameTestB1HasKnight() {
        Piece b1Piece = game.getBoard().getPieceByPos(1, 0);
        assertTrue(b1Piece instanceof Knight);
    }

    @Test
    public void newGameTestC1HasBishop() {
        Piece c1Piece = game.getBoard().getPieceByPos(2, 0);
        assertTrue(c1Piece instanceof Bishop);
    }

    @Test
    public void newGameTestD1HasQueen() {
        Piece d1Piece = game.getBoard().getPieceByPos(3, 0);
        assertTrue(d1Piece instanceof Queen);
    }

    @Test
    public void newGameTestE1HasKing() {
        Piece e1Piece = game.getBoard().getPieceByPos(4, 0);
        assertTrue(e1Piece instanceof King);
    }

    @Test
    public void newGameTestF1HasBishop() {
        Piece f1Piece = game.getBoard().getPieceByPos(5, 0);
        assertTrue(f1Piece instanceof Bishop);
    }

    @Test
    public void newGameTestG1HasKnight() {
        Piece g1Piece = game.getBoard().getPieceByPos(6, 0);
        assertTrue(g1Piece instanceof Knight);
    }

    @Test
    public void newGameTestH1HasRook() {
        Piece h1Piece = game.getBoard().getPieceByPos(7, 0);
        assertTrue(h1Piece instanceof Rook);
    }

    @Test
    public void newGameTestA2HasPawn() {
        Piece a2Piece = game.getBoard().getPieceByPos(0, 1);
        assertTrue(a2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestB2HasPawn() {
        Piece b2Piece = game.getBoard().getPieceByPos(1, 1);
        assertTrue(b2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestC2HasPawn() {
        Piece c2Piece = game.getBoard().getPieceByPos(2, 1);
        assertTrue(c2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestD2HasPawn() {
        Piece d2Piece = game.getBoard().getPieceByPos(3, 1);
        assertTrue(d2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestE2HasPawn() {
        Piece e2Piece = game.getBoard().getPieceByPos(4, 1);
        assertTrue(e2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestF2HasPawn() {
        Piece f2Piece = game.getBoard().getPieceByPos(5, 1);
        assertTrue(f2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestG2HasPawn() {
        Piece g2Piece = game.getBoard().getPieceByPos(6, 1);
        assertTrue(g2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestH2HasPawn() {
        Piece h2Piece = game.getBoard().getPieceByPos(7, 1);
        assertTrue(h2Piece instanceof Pawn);
    }

    @Test
    public void newGameTestA7HasPawn() {
        Piece a7Piece = game.getBoard().getPieceByPos(0, 6);
        assertTrue(a7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestB7HasPawn() {
        Piece b7Piece = game.getBoard().getPieceByPos(1, 6);
        assertTrue(b7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestC7HasPawn() {
        Piece c7Piece = game.getBoard().getPieceByPos(2, 6);
        assertTrue(c7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestD7HasPawn() {
        Piece d7Piece = game.getBoard().getPieceByPos(3, 6);
        assertTrue(d7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestE7HasPawn() {
        Piece e7Piece = game.getBoard().getPieceByPos(4, 6);
        assertTrue(e7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestF7HasPawn() {
        Piece f7Piece = game.getBoard().getPieceByPos(5, 6);
        assertTrue(f7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestG7HasPawn() {
        Piece g7Piece = game.getBoard().getPieceByPos(6, 6);
        assertTrue(g7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestH7HasPawn() {
        Piece h7Piece = game.getBoard().getPieceByPos(7, 6);
        assertTrue(h7Piece instanceof Pawn);
    }

    @Test
    public void newGameTestA8HasRook() {
        Piece a8Piece = game.getBoard().getPieceByPos(0, 7);
        assertTrue(a8Piece instanceof Rook);
    }

    @Test
    public void newGameTestB8HasKnight() {
        Piece b8Piece = game.getBoard().getPieceByPos(1, 7);
        assertTrue(b8Piece instanceof Knight);
    }

    @Test
    public void newGameTestC8HasBishop() {
        Piece c8Piece = game.getBoard().getPieceByPos(2, 7);
        assertTrue(c8Piece instanceof Bishop);
    }

    @Test
    public void newGameTestD8HasQueen() {
        Piece d8Piece = game.getBoard().getPieceByPos(3, 7);
        assertTrue(d8Piece instanceof Queen);
    }

    @Test
    public void newGameTestE8HasKing() {
        Piece e8Piece = game.getBoard().getPieceByPos(4, 7);
        assertTrue(e8Piece instanceof King);
    }

    @Test
    public void newGameTestF8HasBishop() {
        Piece f8Piece = game.getBoard().getPieceByPos(5, 7);
        assertTrue(f8Piece instanceof Bishop);
    }

    @Test
    public void newGameTestG8HasKnight() {
        Piece g8Piece = game.getBoard().getPieceByPos(6, 7);
        assertTrue(g8Piece instanceof Knight);
    }

    @Test
    public void newGameTestH8HasRook() {
        Piece h8Piece = game.getBoard().getPieceByPos(7, 7);
        assertTrue(h8Piece instanceof Rook);
    }
}
