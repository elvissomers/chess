package classes.controllers;

import classes.board.Board;
import classes.pieces.*;

public class Game {

    private Board board;

    private Player whitePlayer;

    private Player blackPlayer;

    public Game() {
        this.board = new Board(8, 8);
        this.whitePlayer = new Player(Player.Team.WHITE);
        this.blackPlayer = new Player(Player.Team.BLACK);
        this.setStartBoard();
    }

    public void setStartBoard(){
        Piece[] piecesInOrder = new Piece[] {
                new Rook(null), new Knight(null), new Bishop(null), new Queen(null), new King(null),
                new Bishop(null), new Knight(null), new Rook(null)
        };
        for (Piece.Team team : new Piece.Team[] {Piece.Team.WHITE, Piece.Team.BLACK}) {
            int yForMajorPieces = (team == Piece.Team.WHITE) ? 0 : 7;
            int yForPawns = (team == Piece.Team.WHITE) ? 1 : 6;

            // Set major pieces
            for (int xPos = 0; xPos < piecesInOrder.length; xPos++) {
                Piece piece = piecesInOrder[xPos];
                piece.setTeam(team);
                this.board.getSquareByPos(xPos, yForMajorPieces).setPiece(piece);
            }

            // Set pawns
            for (int xPos = 0; xPos < this.board.getHorizontalSize(); xPos++){
                this.board.getSquareByPos(xPos, yForPawns).setPiece(new Pawn(team));
            }
        }
    }
}
