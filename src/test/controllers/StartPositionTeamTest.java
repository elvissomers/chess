package test.controllers;

import main.classes.controllers.Game;
import main.classes.pieces.*;
import org.junit.jupiter.api.Test;
import main.classes.structures.Team;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartPositionTeamTest {
    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
    }

    @Test
    public void newGameTestA1IsWhite() {
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        assertEquals(Team.WHITE, a1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestB1IsWhite() {
        Piece b1Piece = game.getBoard().getPieceByPos(1, 0);
        assertEquals(Team.WHITE, b1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestC1IsWhite() {
        Piece c1Piece = game.getBoard().getPieceByPos(2, 0);
        assertEquals(Team.WHITE, c1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestD1IsWhite() {
        Piece d1Piece = game.getBoard().getPieceByPos(3, 0);
        assertEquals(Team.WHITE, d1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestE1IsWhite() {
        Piece e1Piece = game.getBoard().getPieceByPos(4, 0);
        assertEquals(Team.WHITE, e1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestF1IsWhite() {
        Piece f1Piece = game.getBoard().getPieceByPos(5, 0);
        assertEquals(Team.WHITE, f1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestG1IsWhite() {
        Piece g1Piece = game.getBoard().getPieceByPos(6, 0);
        assertEquals(Team.WHITE, g1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestH1IsWhite() {
        Piece h1Piece = game.getBoard().getPieceByPos(7, 0);
        assertEquals(Team.WHITE, h1Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestA2IsWhite() {
        Piece a2Piece = game.getBoard().getPieceByPos(0, 1);
        assertEquals(Team.WHITE, a2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestB2IsWhite() {
        Piece b2Piece = game.getBoard().getPieceByPos(1, 1);
        assertEquals(Team.WHITE, b2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestC2IsWhite() {
        Piece c2Piece = game.getBoard().getPieceByPos(2, 1);
        assertEquals(Team.WHITE, c2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestD2IsWhite() {
        Piece d2Piece = game.getBoard().getPieceByPos(3, 1);
        assertEquals(Team.WHITE, d2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestE2IsWhite() {
        Piece e2Piece = game.getBoard().getPieceByPos(4, 1);
        assertEquals(Team.WHITE, e2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestF2IsWhite() {
        Piece f2Piece = game.getBoard().getPieceByPos(5, 1);
        assertEquals(Team.WHITE, f2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestG2IsWhite() {
        Piece g2Piece = game.getBoard().getPieceByPos(6, 1);
        assertEquals(Team.WHITE, g2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestH2IsWhite() {
        Piece h2Piece = game.getBoard().getPieceByPos(7, 1);
        assertEquals(Team.WHITE, h2Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestA7IsBlack() {
        Piece a7Piece = game.getBoard().getPieceByPos(0, 6);
        assertEquals(Team.BLACK, a7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestB7IsBlack() {
        Piece b7Piece = game.getBoard().getPieceByPos(1, 6);
        assertEquals(Team.BLACK, b7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestC7IsBlack() {
        Piece c7Piece = game.getBoard().getPieceByPos(2, 6);
        assertEquals(Team.BLACK, c7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestD7IsBlack() {
        Piece d7Piece = game.getBoard().getPieceByPos(3, 6);
        assertEquals(Team.BLACK, d7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestE7IsBlack() {
        Piece e7Piece = game.getBoard().getPieceByPos(4, 6);
        assertEquals(Team.BLACK, e7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestF7IsBlack() {
        Piece f7Piece = game.getBoard().getPieceByPos(5, 6);
        assertEquals(Team.BLACK, f7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestG7IsBlack() {
        Piece g7Piece = game.getBoard().getPieceByPos(6, 6);
        assertEquals(Team.BLACK, g7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestH7IsBlack() {
        Piece h7Piece = game.getBoard().getPieceByPos(7, 6);
        assertEquals(Team.BLACK, h7Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestA8IsBlack() {
        Piece a8Piece = game.getBoard().getPieceByPos(0, 7);
        assertEquals(Team.BLACK, a8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestB8IsBlack() {
        Piece b8Piece = game.getBoard().getPieceByPos(1, 7);
        assertEquals(Team.BLACK, b8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestC8IsBlack() {
        Piece c8Piece = game.getBoard().getPieceByPos(2, 7);
        assertEquals(Team.BLACK, c8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestD8IsBlack() {
        Piece d8Piece = game.getBoard().getPieceByPos(3, 7);
        assertEquals(Team.BLACK, d8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestE8IsBlack() {
        Piece e8Piece = game.getBoard().getPieceByPos(4, 7);
        assertEquals(Team.BLACK, e8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestF8IsBlack() {
        Piece f8Piece = game.getBoard().getPieceByPos(5, 7);
        assertEquals(Team.BLACK, f8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestG8IsBlack() {
        Piece g8Piece = game.getBoard().getPieceByPos(6, 7);
        assertEquals(Team.BLACK, g8Piece.getPlayer().getTeam());
    }

    @Test
    public void newGameTestH8IsBlack() {
        Piece h8Piece = game.getBoard().getPieceByPos(7, 7);
        assertEquals(Team.BLACK, h8Piece.getPlayer().getTeam());
    }
}
