package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Pawn;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.goesBackAndForth;
import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.playerInTurn;

@Service
@AllArgsConstructor
public class DrawRuleService {

    private final PlayerService playerService;

    public void setOtherDraws(Game game) {
        if (hasFiftyMoveDraw(game) || hasThreeFoldDraw(game))
            game.setGameState(GameState.DRAW);
    }

    private boolean hasThreeFoldDraw(Game game) {
        // TODO: Use real three fold draw rule
        return (playerHasThreeFoldDraw(game.getWhitePlayer()) && playerHasThreeFoldDraw(game.getBlackPlayer()));
    }

    private boolean playerHasThreeFoldDraw(Player player) {
        int historySize = playerService.getNumberOfMoves(player.getId());
        if (historySize < 6)
            return false;

        List<Move> lastSixMoves = playerService.getLastNMoves(6, player.getId());
        return (goesBackAndForth(lastSixMoves));
    }

    private boolean hasFiftyMoveDraw(Game game) {
        return playerHasFiftyMoveDraw(playerInTurn(game));
    }

    private boolean playerHasFiftyMoveDraw(Player player) {
        if (playerService.getNumberOfMoves(player.getId()) < 50)
            return false;

        List<Move> lastFiftyMoves = playerService.getLastNMoves(50, player.getId());
        for (Move move : lastFiftyMoves) {
            if (move.getPiece() instanceof Pawn || move.getTakenPiece() == null) {
                return false;
            }
        }
        return true;
    }
}
