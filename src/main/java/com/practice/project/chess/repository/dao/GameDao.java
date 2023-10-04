package com.practice.project.chess.repository.dao;

import com.practice.project.chess.repository.enums.GameState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GameDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState gameState;

    @OneToOne(mappedBy = "game")
    private PlayerDao whitePlayer;

    @OneToOne(mappedBy = "game")
    private PlayerDao blackPlayer;
}
