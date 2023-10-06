package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.King;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.project.chess.service.logic.AllUtil.getOpponentPlayer;
import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.*;
import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.playerInTurn;
import static com.practice.project.chess.service.logic.game.util.PlayerUtil.getPlayerKing;

@Service
@AllArgsConstructor
public class GameStateService {

    private final PlayerService playerService;
    private final LegalMoveService legalMoveService;
    private final DrawRuleService drawRuleService;

    public void updateGameState(Game game) {
        drawRuleService.setOtherDraws(game);
        updatePlayerTurn(game);
        setCheckOrStaleMate(game);
    }

    private void setCheckOrStaleMate(Game game) {
        Player playerInTurn = playerInTurn(game);
        setPlayerKingInCheck(game, playerInTurn, getOpponentPlayer(game, playerInTurn));
        if (!canPlayerMove(playerInTurn, game)) {
            if (isPlayerInCheck(playerInTurn))
                game.setGameState(opponentWins(playerInTurn));
            else
                game.setGameState(GameState.DRAW);
        }
    }

    private void setPlayerKingInCheck(Game game, Player player, Player attackingPlayer) {
        King playerKing = getPlayerKing(player);
        playerService.setAllAttackedAndMovableSquaresForPlayer(game, attackingPlayer);
        List<Coordinate> attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        playerKing.setInCheck(attackedSquares.contains(new Coordinate(playerKing.getHorizontalPosition(), playerKing.getVerticalPosition())));
    }

    private boolean canPlayerMove(Player player, Game game) {
        legalMoveService.setAllLegalMovableSquaresForPlayer(player, game);
        return !playerService.getAllMovableSquaresForPlayer(player).isEmpty();
    }
}
