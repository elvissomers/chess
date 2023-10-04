package com.practice.project.chess.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerDao, Long> {
}
