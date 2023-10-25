package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.constants.BoardSize;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.structures.BoardMap;
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

    public static Player getPlayerOfOtherTeam(Game game, Team team) {
        return (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
    }

    public static int pawnStartRank(Pawn pawn) {
        return (pawn.getTeam() == Team.WHITE) ? 1 : 6;
    }

    public static boolean enemyTeam(Piece pawn, Pawn otherPawn) {
        return (otherPawn.getTeam() != pawn.getTeam());
    }

    public static boolean withinBoard(int x, int y) {
        return (x >= 0 && x < BoardSize.horizontalSize) && (y >= 0 && y < BoardSize.verticalSize);
    }

    public static void addMovableSquareIfEmptyOrEnemy(int x, int y, Piece piece, BoardMap board) {
        Piece otherPiece = board.getPieceByPos(x, y);
        if (otherPiece == null || otherPiece.getTeam() != piece.getTeam()) {
            piece.addMovableSquare(board.getCoordinateByPos(x, y));
        }
    }

    static boolean hasPiece(int x, int y, BoardMap board) {
        return (board.getPieceByPos(x, y) != null);
    }
}
