package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.structures.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ordina.nl.chess.instances.Player;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByGameAndTeam(Game game, Team team);
}
