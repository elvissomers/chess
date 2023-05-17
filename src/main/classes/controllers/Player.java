package main.classes.controllers;

import main.classes.board.Square;
import main.classes.pieces.Piece;

import java.util.List;

public class Player {

    private Game game;

    public enum Team {
        WHITE, BLACK
    }

    private final Player.Team team;

    // TODO: create specific pieceList class
    private List<Piece> pieces;

    // TODO: create specific class here too (instead of list)
    private List<Square> coveredSquares;

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Player(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Square> getCoveredSquares() {
        return coveredSquares;
    }

    public void setCoveredSquares(List<Square> coveredSquares) {
        this.coveredSquares = coveredSquares;
    }

    public void movePiece(Piece piece, Square destSquare) {
        piece.setSquare(destSquare);
    }
}
