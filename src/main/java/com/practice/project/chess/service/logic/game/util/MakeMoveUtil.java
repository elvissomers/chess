package com.practice.project.chess.service.logic.game.util;

import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.GameState;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.exception.InvalidMoveException;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Piece;

import java.util.List;

import static com.practice.project.chess.service.logic.game.util.PlayerUtil.getPlayerKing;

public final class MakeMoveUtil {

    private MakeMoveUtil() {
    }

    public static Team getOtherTeam(Team team) {
        return (team == Team.WHITE) ? Team.BLACK : Team.WHITE;
    }

    public static CastleType getTypeIfCastled(Move move) {
        if (move.getPiece().getPieceType() == PieceType.KING) {
            if (move.getHorizontalFrom() - move.getHorizontalTo() == 2)
                return CastleType.LONG;
            else if (move.getHorizontalFrom() - move.getHorizontalTo() == -2)
                return CastleType.SHORT;
        }
        return null;
    }

    public static PieceType getNewPieceIfPromoted(Move move) {
        // TODO 2) Get the wanted PieceType as input from the used instead of just hardcoding it to queen
        if (move.getPiece().getPieceType() == PieceType.PAWN) {
            if (move.getVerticalTo() == getPromotionRank(move.getPiece()))
                return PieceType.QUEEN;
        }
        return null;
    }

    private static int getPromotionRank(Piece piece) {
        return (piece.getTeam() == Team.WHITE) ? 7 : 0;
    }

    public static void checkIfPieceInTurn(Game game, Piece piece) {
        if ((game.getGameState() == GameState.WHITE_TURN && piece.getTeam() == Team.WHITE)
                || (game.getGameState() == GameState.BLACK_TURN && piece.getTeam() == Team.BLACK)) {
            return;
        }
        throw new InvalidMoveException("Not players turn!");
    }

    public static void updatePlayerTurn(Game game) {
        GameState nextPlayerTurn = (game.getGameState() == GameState.WHITE_TURN) ? GameState.BLACK_TURN
                : GameState.WHITE_TURN;
        game.setGameState(nextPlayerTurn);
    }

    public static boolean isPlayerInCheck(Player player) {
        return getPlayerKing(player).isInCheck();
    }

    public static GameState opponentWins(Player player) {
        return (player.getTeam() == Team.WHITE) ? GameState.BLACK_WINS : GameState.WHITE_WINS;
    }

    public static boolean goesBackAndForth(List<Move> moves) {
        return (moves.get(0).equals(moves.get(2)) && moves.get(2).equals(moves.get(4)) &&
                moves.get(1).equals(moves.get(3)) && moves.get(3).equals(moves.get(5)));
    }


    public static Player playerInTurn(Game game) {
        if (game.getGameState() == GameState.WHITE_TURN)
            return game.getWhitePlayer();
        else if (game.getGameState() == GameState.BLACK_TURN)
            return game.getBlackPlayer();
        else
            throw new ElementNotFoundException("No Player in turn!");
    }
}
