package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.dto.GameDto;
import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("game")
@CrossOrigin(maxAge = 3600)
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("view/{id}")
    public GameDto getGame(@PathVariable long id) {
        // TODO: setup GameDto!
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return null;
        }
        // TODO: GameDto = optionalGame.get().toDto();
        return null;
    }

    @PutMapping("create_new")
    public void createNewStandardGame() {
        Game game = new Game();
        game.setStandardStartingGame();
        gameRepository.save(game);
    }
}
