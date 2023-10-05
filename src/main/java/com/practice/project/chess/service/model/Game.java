package com.practice.project.chess.service.model;

import com.practice.project.chess.repository.enums.GameState;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState gameState;

    @OneToOne(mappedBy = "game")
    private Player whitePlayer;

    @OneToOne(mappedBy = "game")
    private Player blackPlayer;
}
