package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.structures.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ordina.nl.chess.pieces.Piece;

import java.util.Optional;

public interface PieceRepository extends JpaRepository<Piece, Long> {

    Optional<Piece> findByPlayerAndCoordinate(Player player, Coordinate coordinate);
}