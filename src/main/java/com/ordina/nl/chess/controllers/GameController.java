package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GameController {

    @Autowired
    private GameRepository gameRepository;

    public void createNewStandardGame() {
        Game game = new Game();
        game.setStandardStartingGame();
        gameRepository.save(game);
    }
}
