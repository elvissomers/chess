package com.practice.project.chess.repository;

import com.practice.project.chess.repository.dao.PlayerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerDao, Long> {
}
