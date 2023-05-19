package test.controllers;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.pieces.*;
import main.classes.controllers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.classes.structures.Team;

public class GameTest {
    private Game Game;

    @BeforeEach
    public void setUp() {
        Game = new Game();
    }

    @Test
    public void testSetStartBoardTeams() {
        Board board = Game.getBoard();

        // Check major pieces for both teams
        for (int xPos = 0; xPos < board.getHorizontalSize(); xPos++) {
            // Check white team
            Piece whitePiece = board.getSquareByPos(xPos, 0).getPiece();
            assertEquals(Team.WHITE, whitePiece.getTeam());

            // Check black team
            Piece blackPiece = board.getSquareByPos(xPos, 7).getPiece();
            assertEquals(Team.BLACK, blackPiece.getTeam());
        }

        // Check pawns for both teams
        for (int xPos = 0; xPos < board.getHorizontalSize(); xPos++) {
            // Check white team
            Piece whitePawn = board.getSquareByPos(xPos, 1).getPiece();
            assertEquals(Team.WHITE, whitePawn.getTeam());

            // Check black team
            Piece blackPawn = board.getSquareByPos(xPos, 6).getPiece();
            assertEquals(Team.BLACK, blackPawn.getTeam());
        }
    }

    @Test
    public void testSetStartBoardPieces() {
        Board board = Game.getBoard();

        // Array representing the correct order of major pieces
        Class[] correctOrder = new Class[] {
                Rook.class, Knight.class, Bishop.class,
                Queen.class, King.class, Bishop.class,
                Knight.class, Rook.class
        };

        // Check major pieces for both teams
        for (int xPos = 0; xPos < board.getHorizontalSize(); xPos++) {
            // Check white team
            Piece whitePiece = board.getSquareByPos(xPos, 0).getPiece();
            assertTrue(correctOrder[xPos].isInstance(whitePiece));

            // Check black team
            Piece blackPiece = board.getSquareByPos(xPos, 7).getPiece();
            assertTrue(correctOrder[xPos].isInstance(blackPiece));
        }

        // Check pawns for both teams
        for (int xPos = 0; xPos < board.getHorizontalSize(); xPos++) {
            // Check white team
            Piece whitePawn = board.getSquareByPos(xPos, 1).getPiece();
            assertTrue(whitePawn instanceof Pawn);

            // Check black team
            Piece blackPawn = board.getSquareByPos(xPos, 6).getPiece();
            assertTrue(blackPawn instanceof Pawn);
        }
    }
}
