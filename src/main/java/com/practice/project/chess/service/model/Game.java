package com.practice.project.chess.service.model;

import com.practice.project.chess.repository.enums.GameState;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Game {

    private long id;
    private GameState gameState;

    private Player whitePlayer;
    private Player blackPlayer;
}
