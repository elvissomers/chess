package test.controllers;

import main.classes.controllers.Game;
import main.classes.pieces.*;
import main.classes.structures.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartPositionTeamTest {

    private Game game;

    static Stream<Boolean> copyBoard() {
        return Stream.of(Boolean.TRUE, Boolean.FALSE);
    }

    @BeforeEach
    public void setup() {
        game = new Game();
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        assertEquals(Team.WHITE, a1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece b1Piece = game.getBoard().getPieceByPos(1, 0);
        assertEquals(Team.WHITE, b1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece c1Piece = game.getBoard().getPieceByPos(2, 0);
        assertEquals(Team.WHITE, c1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece d1Piece = game.getBoard().getPieceByPos(3, 0);
        assertEquals(Team.WHITE, d1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece e1Piece = game.getBoard().getPieceByPos(4, 0);
        assertEquals(Team.WHITE, e1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece f1Piece = game.getBoard().getPieceByPos(5, 0);
        assertEquals(Team.WHITE, f1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece g1Piece = game.getBoard().getPieceByPos(6, 0);
        assertEquals(Team.WHITE, g1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH1IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece h1Piece = game.getBoard().getPieceByPos(7, 0);
        assertEquals(Team.WHITE, h1Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece a2Piece = game.getBoard().getPieceByPos(0, 1);
        assertEquals(Team.WHITE, a2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece b2Piece = game.getBoard().getPieceByPos(1, 1);
        assertEquals(Team.WHITE, b2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece c2Piece = game.getBoard().getPieceByPos(2, 1);
        assertEquals(Team.WHITE, c2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece d2Piece = game.getBoard().getPieceByPos(3, 1);
        assertEquals(Team.WHITE, d2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece e2Piece = game.getBoard().getPieceByPos(4, 1);
        assertEquals(Team.WHITE, e2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece f2Piece = game.getBoard().getPieceByPos(5, 1);
        assertEquals(Team.WHITE, f2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece g2Piece = game.getBoard().getPieceByPos(6, 1);
        assertEquals(Team.WHITE, g2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH2IsWhite(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece h2Piece = game.getBoard().getPieceByPos(7, 1);
        assertEquals(Team.WHITE, h2Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece a7Piece = game.getBoard().getPieceByPos(0, 6);
        assertEquals(Team.BLACK, a7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece b7Piece = game.getBoard().getPieceByPos(1, 6);
        assertEquals(Team.BLACK, b7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece c7Piece = game.getBoard().getPieceByPos(2, 6);
        assertEquals(Team.BLACK, c7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece d7Piece = game.getBoard().getPieceByPos(3, 6);
        assertEquals(Team.BLACK, d7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece e7Piece = game.getBoard().getPieceByPos(4, 6);
        assertEquals(Team.BLACK, e7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece f7Piece = game.getBoard().getPieceByPos(5, 6);
        assertEquals(Team.BLACK, f7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece g7Piece = game.getBoard().getPieceByPos(6, 6);
        assertEquals(Team.BLACK, g7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH7IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece h7Piece = game.getBoard().getPieceByPos(7, 6);
        assertEquals(Team.BLACK, h7Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece a8Piece = game.getBoard().getPieceByPos(0, 7);
        assertEquals(Team.BLACK, a8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece b8Piece = game.getBoard().getPieceByPos(1, 7);
        assertEquals(Team.BLACK, b8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece c8Piece = game.getBoard().getPieceByPos(2, 7);
        assertEquals(Team.BLACK, c8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece d8Piece = game.getBoard().getPieceByPos(3, 7);
        assertEquals(Team.BLACK, d8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece e8Piece = game.getBoard().getPieceByPos(4, 7);
        assertEquals(Team.BLACK, e8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece f8Piece = game.getBoard().getPieceByPos(5, 7);
        assertEquals(Team.BLACK, f8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece g8Piece = game.getBoard().getPieceByPos(6, 7);
        assertEquals(Team.BLACK, g8Piece.getPlayer().getTeam());
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH8IsBlack(Boolean copy) {
    if (copy) {
        game = new Game(game);
    }
        Piece h8Piece = game.getBoard().getPieceByPos(7, 7);
        assertEquals(Team.BLACK, h8Piece.getPlayer().getTeam());
    }
}
