package main.classes.controllers;

import main.classes.board.Board;
import main.classes.game.Move;
import main.classes.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;

    private Player whitePlayer;

    private Player blackPlayer;

    private List<Move> moveHistory = new ArrayList<>();

    public Game() {
        this.board = new Board(8, 8);
        this.whitePlayer = new Player(this, Player.Team.WHITE);
        this.blackPlayer = new Player(this, Player.Team.BLACK);
        this.setStartBoard();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setStartBoard(){
        for (Piece.Team team : new Piece.Team[] {Piece.Team.WHITE, Piece.Team.BLACK}) {
            Piece[] piecesInOrder = new Piece[] {
                    new Rook(this, null), new Knight(this, null), new Bishop(this, null),
                    new Queen(this, null), new King(this, null), new Bishop(this, null),
                    new Knight(this, null), new Rook(this, null)
            };
            int yForMajorPieces = (team == Piece.Team.WHITE) ? 0 : 7;
            int yForPawns = (team == Piece.Team.WHITE) ? 1 : 6;

            // Set major pieces
            for (int xPos = 0; xPos < piecesInOrder.length; xPos++) {
                Piece piece = piecesInOrder[xPos];
                piece.setTeam(team);
                piece.setSquare(this.board.getSquareByPos(xPos, yForMajorPieces));
                piece.setBoard(this.board);
            }

            // Set pawns
            for (int xPos = 0; xPos < this.board.getHorizontalSize(); xPos++){
                Pawn pawn = new Pawn(this, team);
                pawn.setSquare(this.board.getSquareByPos(xPos, yForPawns));
                pawn.setBoard(this.board);
            }
        }
    }
}
