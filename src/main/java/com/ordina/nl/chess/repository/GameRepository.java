package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.instances.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
