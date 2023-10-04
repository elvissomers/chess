package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.repository.dao.Game;
import com.practice.project.chess.repository.dao.Player;
import com.practice.project.chess.service.model.pieces.King;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.model.pieces.Rook;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.logic.AllUtil;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CastlingService {

    private final BoardService boardService;
    private final PlayerService playerService;

    private int xPos;
    private int yPos;
    private Team team;

    public void setKingCastlingMoves(Piece piece, Game game) {
        King king = (King) piece;
        setup(king);
        BoardMap board = boardService.getBoardMapForGame(game.getId());

        if (king.isInCheck() || king.isHasMoved())
            return;

        if (canCastleShort(game, board))
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));
        if (canCastleLong(game, board))
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
    }

    private void setup(King king) {
        xPos = king.getHorizontalPosition();
        yPos = king.getVerticalPosition();
        team = king.getPlayer().getTeam();
    }

    private boolean canCastleShort(Game game, BoardMap board) {
        return board.getPieceByPos(xPos+1,yPos) == null &&
                kingIsSafeAt(game, board.getCoordinateByPos(xPos + 1, yPos)) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    private boolean canCastleLong(Game game, BoardMap board) {
        return board.getPieceByPos(xPos-1, yPos) == null &&
                kingIsSafeAt(game, board.getCoordinateByPos(xPos - 1, yPos)) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    public boolean kingIsSafeAt(Game game, Coordinate position) {
        Player attackingPlayer = AllUtil.getOpponentPlayer(game, team);
        playerService.setAllAttackedAndMovableSquaresForPlayer(attackingPlayer);
        List<Coordinate> attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        return !attackedSquares.contains(position);
    }
}
