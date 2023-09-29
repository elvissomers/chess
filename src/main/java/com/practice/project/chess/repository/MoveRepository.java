package com.practice.project.chess.repository;

import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.pieces.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {

    Optional<Move> findByPieceAndTakenPieceAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(Piece piece, Piece takenPiece,
                                                                                                        int xFrom, int xTo, int yFrom, int yTo);
}
