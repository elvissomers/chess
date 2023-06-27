package com.ordina.nl.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ordina.nl.chess.pieces.Piece;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {

    Optional<Piece> findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
            int xPos, int yPos, long id);
}
