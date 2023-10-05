package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.GameDao;
import com.practice.project.chess.service.model.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameMapper {

    private final PlayerMapper playerMapper;

    public Game gameDaoToGame(GameDao dao) {
        return Game.builder()
                .id(dao.getId())
                .gameState(dao.getGameState())
                .whitePlayer(playerMapper.playerDaoToPlayer(dao.getWhitePlayer()))
                .blackPlayer(playerMapper.playerDaoToPlayer(dao.getBlackPlayer()))
                .build();
    }
}
