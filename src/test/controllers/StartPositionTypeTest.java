package test.controllers;

import main.classes.instances.Game;
import main.classes.pieces.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartPositionTypeTest {

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
    public void newGameTestA1HasRook(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece a1Piece = game.getBoard().getPieceByPos(0, 0);
        assertTrue(a1Piece instanceof Rook);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB1HasKnight(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece b1Piece = game.getBoard().getPieceByPos(1, 0);
        assertTrue(b1Piece instanceof Knight);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC1HasBishop(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece c1Piece = game.getBoard().getPieceByPos(2, 0);
        assertTrue(c1Piece instanceof Bishop);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD1HasQueen(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece d1Piece = game.getBoard().getPieceByPos(3, 0);
        assertTrue(d1Piece instanceof Queen);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE1HasKing(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece e1Piece = game.getBoard().getPieceByPos(4, 0);
        assertTrue(e1Piece instanceof King);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF1HasBishop(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece f1Piece = game.getBoard().getPieceByPos(5, 0);
        assertTrue(f1Piece instanceof Bishop);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG1HasKnight(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece g1Piece = game.getBoard().getPieceByPos(6, 0);
        assertTrue(g1Piece instanceof Knight);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH1HasRook(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece h1Piece = game.getBoard().getPieceByPos(7, 0);
        assertTrue(h1Piece instanceof Rook);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece a2Piece = game.getBoard().getPieceByPos(0, 1);
        assertTrue(a2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece b2Piece = game.getBoard().getPieceByPos(1, 1);
        assertTrue(b2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece c2Piece = game.getBoard().getPieceByPos(2, 1);
        assertTrue(c2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece d2Piece = game.getBoard().getPieceByPos(3, 1);
        assertTrue(d2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece e2Piece = game.getBoard().getPieceByPos(4, 1);
        assertTrue(e2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece f2Piece = game.getBoard().getPieceByPos(5, 1);
        assertTrue(f2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece g2Piece = game.getBoard().getPieceByPos(6, 1);
        assertTrue(g2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH2HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece h2Piece = game.getBoard().getPieceByPos(7, 1);
        assertTrue(h2Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece a7Piece = game.getBoard().getPieceByPos(0, 6);
        assertTrue(a7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece b7Piece = game.getBoard().getPieceByPos(1, 6);
        assertTrue(b7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece c7Piece = game.getBoard().getPieceByPos(2, 6);
        assertTrue(c7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece d7Piece = game.getBoard().getPieceByPos(3, 6);
        assertTrue(d7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece e7Piece = game.getBoard().getPieceByPos(4, 6);
        assertTrue(e7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece f7Piece = game.getBoard().getPieceByPos(5, 6);
        assertTrue(f7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece g7Piece = game.getBoard().getPieceByPos(6, 6);
        assertTrue(g7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH7HasPawn(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece h7Piece = game.getBoard().getPieceByPos(7, 6);
        assertTrue(h7Piece instanceof Pawn);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestA8HasRook(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece a8Piece = game.getBoard().getPieceByPos(0, 7);
        assertTrue(a8Piece instanceof Rook);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestB8HasKnight(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece b8Piece = game.getBoard().getPieceByPos(1, 7);
        assertTrue(b8Piece instanceof Knight);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestC8HasBishop(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece c8Piece = game.getBoard().getPieceByPos(2, 7);
        assertTrue(c8Piece instanceof Bishop);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestD8HasQueen(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece d8Piece = game.getBoard().getPieceByPos(3, 7);
        assertTrue(d8Piece instanceof Queen);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestE8HasKing(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece e8Piece = game.getBoard().getPieceByPos(4, 7);
        assertTrue(e8Piece instanceof King);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestF8HasBishop(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece f8Piece = game.getBoard().getPieceByPos(5, 7);
        assertTrue(f8Piece instanceof Bishop);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestG8HasKnight(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece g8Piece = game.getBoard().getPieceByPos(6, 7);
        assertTrue(g8Piece instanceof Knight);
    }

    @ParameterizedTest
    @MethodSource("copyBoard")
    public void newGameTestH8HasRook(Boolean copy) {
        if (copy) {
            game = new Game(game);
        }
        Piece h8Piece = game.getBoard().getPieceByPos(7, 7);
        assertTrue(h8Piece instanceof Rook);
    }
}
