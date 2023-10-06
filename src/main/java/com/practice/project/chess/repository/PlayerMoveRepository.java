package com.practice.project.chess.repository;

import com.practice.project.chess.service.model.movehistory.PlayerMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerMoveRepository extends JpaRepository<PlayerMove, Long> {

    List<PlayerMove> findByPlayer_Id(long playerId);
}
