package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import org.springframework.stereotype.Service;

@Service
public class AllUtil {

    public static Player getOpponentPlayer(Game game, Player player) {
        return (player.getTeam() == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public static Player getOpponentPlayer(Game game, Team team) {
        return (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public static Player getPlayerOfTeam(Game game, Team team) {
        return (team == Team.WHITE) ? game.getWhitePlayer() : game.getBlackPlayer();
    }

    public static int pawnStartRank(Pawn pawn) {
        return (pawn.getTeam() == Team.WHITE) ? 1 : 6;
    }

    public static boolean enemyTeam(Piece pawn, Pawn otherPawn) {
        return (otherPawn.getTeam() != pawn.getTeam());
    }
}
