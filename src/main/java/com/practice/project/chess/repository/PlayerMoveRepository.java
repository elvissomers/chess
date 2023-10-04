package com.practice.project.chess.repository;

import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerMoveRepository extends JpaRepository<PlayerMove, Long> {

    Optional<PlayerMove> findByNumberAndMove(int number, Move move);

    Optional<PlayerMove> findByNumberAndPlayer_Id(int number, long playerId);

    List<PlayerMove> findByPlayer_Id(long playerId);
}
