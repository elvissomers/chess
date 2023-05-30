package main.classes.controllers;

import main.classes.board.Board;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.BoardMap;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private BoardMap board;

    private Player whitePlayer;

    private Player blackPlayer;

    public Game() {
        whitePlayer = new Player(this, Team.WHITE);
        blackPlayer = new Player(this, Team.BLACK);

        board = new BoardMap(this);
    }

//    public Game(Game other) {
//        this.board = new Board(other.getBoard());
//
//        this.whitePlayer = new Player(other.getWhitePlayer());
//        new PieceSet(whitePlayer);
//        whitePlayer.setGame(this);
//        this.blackPlayer = new Player(other.getBlackPlayer());
//        new PieceSet(blackPlayer);
//        blackPlayer.setGame(this);
//    }

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
