package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
@CrossOrigin(maxAge = 3600)
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @PutMapping("create_new")
    public void createNewStandardGame() {
        Game game = new Game();
        game.setStandardStartingGame();
        gameRepository.save(game);
    }
}
