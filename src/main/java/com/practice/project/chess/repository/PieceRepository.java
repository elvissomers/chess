package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.practice.project.chess.service.model.pieces.Piece;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PieceRepository extends JpaRepository<PieceDao, Long> {

    Optional<Piece> findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
            int xPos, int yPos, long id);
}
