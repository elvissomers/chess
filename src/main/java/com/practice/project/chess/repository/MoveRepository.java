package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.model.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<MoveDao, Long> {

    // TODO : MoveDao has DAO piece attributes, so the query should use those instead of
    // TODO - Piece.

    // TODO 2: Should MoveDAO have PieceDAO - attributes at all?
    // seems yes, this is the way of sql
    Optional<MoveDao> findByPiece_IdAndTakenPiece_IdAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalToAndPromotedTo(long pieceId, long takenPieceId,
                                                                                                                              int xFrom, int xTo, int yFrom, int yTo, PieceType promotedTo);
}
