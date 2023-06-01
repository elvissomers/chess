package main.classes.controllers;

import main.classes.structures.BoardMap;
import main.classes.structures.Team;

public class Game {

    private final BoardMap board;

    private final Player whitePlayer;

    private final Player blackPlayer;

    public Game() {
        whitePlayer = new Player(this, Team.WHITE);
        blackPlayer = new Player(this, Team.BLACK);

        board = new BoardMap(this);
    }

    public BoardMap getBoard() {
        return board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }


}
