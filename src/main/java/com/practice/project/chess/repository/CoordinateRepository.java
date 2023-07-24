package com.practice.project.chess.repository;

import com.practice.project.chess.service.structures.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

    Optional<Coordinate> findByXPosAndYPos(int xPos, int yPos);

}
