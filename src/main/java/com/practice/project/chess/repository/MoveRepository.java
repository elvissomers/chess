package com.practice.project.chess.repository;

import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    Optional<Move> findByPieceAndTakenPieceAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(Piece piece, Piece takenPiece,
                                                                                                        int xFrom, int xTo, int yFrom, int yTo);
}
