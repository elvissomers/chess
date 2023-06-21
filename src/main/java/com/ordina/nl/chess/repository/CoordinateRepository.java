package com.ordina.nl.chess.repository;

import com.ordina.nl.chess.structures.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

    Optional<Coordinate> findByXPosAndYPos(int xPos, int yPos);

}
