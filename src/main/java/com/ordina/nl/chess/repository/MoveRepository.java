package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.game.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> findByPlayers_IdContaining(long id);
}
