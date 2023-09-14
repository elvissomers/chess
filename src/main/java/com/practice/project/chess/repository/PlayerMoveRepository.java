package com.practice.project.chess.repository;

import com.practice.project.chess.entity.PlayerMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMoveRepository extends JpaRepository<PlayerMove, Long> {
}
