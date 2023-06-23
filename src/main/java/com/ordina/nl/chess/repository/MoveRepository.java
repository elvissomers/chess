package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.game.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> findByPlayers_IdContaining(long id);


}
