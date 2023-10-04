package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<PieceDao, Long> {
}
