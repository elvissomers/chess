package com.practice.project.chess.service.game;

import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.pieces.Pawn;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.constants.BoardSize;
import com.practice.project.chess.service.player.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EnPassantService {

    private int xPos;
    private int yPos;
    private int startPos;
    private int yDirection;

    private final BoardService boardService;
    private final PlayerService playerService;

    public void addPawnEnPassantMovesToMovableSquares(Piece pawn, Game game) {
        BoardMap board = boardService.getBoardMapForGame(game.getId());

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0)
                addPawnEnPassantMovesDirection(-1, board, pawn, game);
            if (xPos + 1 < BoardSize.horizontalSize)
                addPawnEnPassantMovesDirection(1, board, pawn, game);
        }
    }

    private void addPawnEnPassantMovesDirection(int direction, BoardMap board, Piece pawn, Game game) {
        if (board.get(board.getCoordinateArray()[xPos + direction][yPos]) instanceof Pawn otherPawn
                && enemyTeam(pawn, otherPawn)) {
            if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos + direction][yPos + yDirection];
                pawn.addMovableSquare(squareInFrontLeft);
            }
        }
    }

    private boolean enemyTeam(Piece pawn, Pawn otherPawn) {
        return (otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam());
    }

    private boolean pawnCanBeTakenEnPassantByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player opponentPlayer = (targetPawn.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        Move opponentLastMove = playerService.getLastMove(opponentPlayer.getId());
        return opponentLastMove.getPiece() == targetPawn && opponentLastMove.getVerticalFrom()
                == ((attackingPawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6);
    }
}
