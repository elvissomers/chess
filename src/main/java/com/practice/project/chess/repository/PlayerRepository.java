package com.practice.project.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practice.project.chess.service.model.Player;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
