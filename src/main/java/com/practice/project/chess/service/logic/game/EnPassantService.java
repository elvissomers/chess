package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.constants.BoardSize;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.practice.project.chess.repository.enums.Team.WHITE;
import static com.practice.project.chess.service.logic.AllUtil.*;

@RequiredArgsConstructor
@Service
public class EnPassantService {

    private final BoardService boardService;
    private final PlayerService playerService;

    public void addPawnEnPassantMovesToMovableSquares(Piece piece, Game game) {
        BoardMap board = boardService.getBoardMapForGame(game.getId());
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();
        int startPos = pawnStartRank((Pawn) piece);
        int yDirection = (piece.getTeam() == WHITE) ? 1 : -1;

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0)
                addPawnEnPassantMovesDirection(-1, board, piece, game);
            if (xPos + 1 < BoardSize.HORIZONTAL_SIZE)
                addPawnEnPassantMovesDirection(1, board, piece, game);
        }
    }

    private void addPawnEnPassantMovesDirection(int direction, BoardMap board, Piece piece, Game game) {
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();
        int yDirection = (piece.getTeam() == WHITE) ? 1 : -1;

        if (board.get(board.getCoordinateArray()[xPos + direction][yPos]) instanceof Pawn otherPawn
                && enemyTeam(piece, otherPawn)) {
            if (pawnCanBeTakenEnPassant(otherPawn, game)) {
                Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos + direction][yPos + yDirection];
                piece.addMovableSquare(squareInFrontLeft);
            }
        }
    }

    private boolean pawnCanBeTakenEnPassant(Pawn targetPawn, Game game) {
        Player opponentPlayer = getPlayerOfTeam(game, targetPawn.getTeam());
        Move opponentLastMove = playerService.getLastMove(opponentPlayer.getId());
        return opponentLastMove.getPiece() == targetPawn && opponentLastMove.getVerticalFrom()
                == (pawnStartRank(targetPawn));
    }
}
