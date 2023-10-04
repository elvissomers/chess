package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.constants.BoardSize;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.practice.project.chess.repository.enums.Team.WHITE;

@RequiredArgsConstructor
@Service
public class EnPassantService {

    private final BoardService boardService;
    private final PlayerService playerService;

    private int xPos;
    private int yPos;
    private int startPos;
    private int yDirection;

    public void addPawnEnPassantMovesToMovableSquares(Piece pawn, Game game) {
        BoardMap board = boardService.getBoardMapForGame(game.getId());
        setup(pawn);

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0)
                addPawnEnPassantMovesDirection(-1, board, pawn, game);
            if (xPos + 1 < BoardSize.horizontalSize)
                addPawnEnPassantMovesDirection(1, board, pawn, game);
        }
    }

    private void setup(Piece pawn) {
        xPos = pawn.getHorizontalPosition();
        yPos = pawn.getVerticalPosition();
        startPos = (pawn.getTeam() == WHITE) ? 1 : 6;
        yDirection = (pawn.getTeam() == WHITE) ? 1 : -1;
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
        return (otherPawn.getTeam() != pawn.getTeam());
    }

    private boolean pawnCanBeTakenEnPassantByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player opponentPlayer = (targetPawn.getTeam() == WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        Move opponentLastMove = playerService.getLastMove(opponentPlayer.getId());
        return opponentLastMove.getPiece() == targetPawn && opponentLastMove.getVerticalFrom()
                == ((attackingPawn.getTeam() == Team.BLACK) ? 1 : 6);
    }
}