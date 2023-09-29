package com.practice.project.chess.service;

import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.enums.Team;
import org.springframework.stereotype.Service;

@Service
public class AllUtil {

    public static Player getOpponentPlayer(Game game, Player player) {
        return (player.getTeam() == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }
}