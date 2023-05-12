package classes.controllers;

import classes.board.Square;
import classes.pieces.Piece;

import java.util.List;

public class Player {

    public enum Team {
        WHITE, BLACK
    }

    private Team team;

    // TODO: create specific pieceList class
    private List<Piece> pieces;

    // TODO: create specific class here too (instead of list)
    private List<Square> coveredSquares;

    public Player(Team team) {
        this.team = team;
    }

    public void movePiece(Piece piece, Square destSquare) {
        piece.setSquare(destSquare);
    }
}
