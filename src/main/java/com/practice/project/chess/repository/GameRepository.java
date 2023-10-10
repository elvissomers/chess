package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.GameDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameDao, Long> {
}
