package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PieceRepository extends JpaRepository<PieceDao, Long> {
    // This repository should be filled with all possible (1024) pieceDao's!
    Optional<PieceDao> findByHorizontalPositionAndVerticalPositionAndTeamAndPieceTypeAndHasMoved(int x, int y, Team team,
                                                                                                 PieceType pieceType, boolean hasMoved);
}
