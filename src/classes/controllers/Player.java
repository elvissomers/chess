package classes.controllers;

import classes.pieces.Piece;

import java.util.List;

public class Player {

    private enum Team {
        WHITE, BLACK
    }

    private Team team;

    private List<Piece> pieces;
}
