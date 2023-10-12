package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.model.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<MoveDao, Long> {

    Optional<MoveDao> findByPieceAndTakenPieceAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalToAndPromotedTo(PieceDao piece, PieceDao takenPiece,
                                                                                                                        int xFrom, int xTo, int yFrom, int yTo, PieceType promotedTo);
}
