package test.controllers;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.pieces.*;
import main.classes.controllers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.classes.structures.Team;

public class MovesTest {
    private Game Game;

    @BeforeEach
    public void setUp() {
        Game = new Game();
    }
}