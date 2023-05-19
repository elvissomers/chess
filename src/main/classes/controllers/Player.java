package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.Piece;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private PieceSet pieceSet;

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void movePiece(Piece piece, Square destSquare) {
        Square fromSquare = piece.getSquare();
        Move move = new Move(piece, fromSquare, destSquare);
        piece.setSquare(destSquare);
        moveHistory.add(move);
        this.game.getMoveHistory().add(move);
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }
}
