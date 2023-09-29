package com.practice.project.chess.controller;

import com.practice.project.chess.controller.dto.GameDto;
import com.practice.project.chess.controller.dto.MovePieceDto;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("game")
@CrossOrigin(maxAge = 3600)
public class GameController {

    private final GameService gameService;

    @GetMapping("view/{id}")
    public GameDto getGame(@PathVariable long id) throws ElementNotFoundException {
        return gameService.getGameDto(id);
    }

    @PutMapping("create_new")
    public GameDto createNewStandardGame() {
        return gameService.getNewGame();
    }

    @PutMapping("make_move/{id}")
    public void makeMove(@PathVariable long id, @RequestBody MovePieceDto moveDto) {
        // TODO: should return gameDto of the new game
        gameService.makeMoveFromDto(moveDto);
    }
}
