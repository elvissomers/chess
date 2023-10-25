package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
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

    public void setKingCastlingMoves(Piece piece, Game game) {
        King king = (King) piece;
        BoardMap board = boardService.getBoardMapForGame(game.getId());
        int xPos = king.getCoordinate().getXPos();
        int yPos = king.getCoordinate().getYPos();

        if (king.isInCheck() || king.isHasMoved())
            return;

        if (canCastleShort(king.getTeam(), xPos, yPos, game, board))
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));
        if (canCastleLong(king.getTeam(), xPos, yPos, game, board))
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
    }

    private boolean canCastleShort(Team team, int xPos, int yPos, Game game, BoardMap board) {
        return board.getPieceByPos(xPos+1,yPos) == null &&
                kingIsSafeAt(team, game, board.getCoordinateByPos(xPos + 1, yPos)) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    private boolean canCastleLong(Team team, int xPos, int yPos, Game game, BoardMap board) {
        return board.getPieceByPos(xPos-1, yPos) == null &&
                kingIsSafeAt(team, game, board.getCoordinateByPos(xPos - 1, yPos)) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    public boolean kingIsSafeAt(Team team, Game game, Coordinate position) {
        Player attackingPlayer = AllUtil.getOpponentPlayer(game, team);
        playerService.setAllAttackedAndMovableSquaresForPlayer(game, attackingPlayer);
        List<Coordinate> attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        return !attackedSquares.contains(position);
    }
}
