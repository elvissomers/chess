package com.practice.project.chess.repository;

import com.practice.project.chess.entity.Move;
import com.practice.project.chess.entity.PlayerMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerMoveRepository extends JpaRepository<PlayerMove, Long> {

    Optional<PlayerMove> findByNumberAndMove(int number, Move move);
}
