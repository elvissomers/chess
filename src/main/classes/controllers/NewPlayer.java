package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewPlayer {

    private Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private Set<Piece> pieces;

    private King king;

    public NewPlayer(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Team getTeam() {
        return team;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public King getKing() {
        return king;
    }
}