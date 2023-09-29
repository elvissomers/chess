package com.practice.project.chess.repository;

import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.enums.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practice.project.chess.repository.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByGameAndTeam(Game game, Team team);
}
