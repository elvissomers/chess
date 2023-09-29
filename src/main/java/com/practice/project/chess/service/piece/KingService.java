package com.practice.project.chess.service.piece;

import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.pieces.King;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.entity.pieces.Rook;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.AllUtil;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.service.game.GameService;
import com.practice.project.chess.service.player.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KingService {

    private final BoardService boardService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final MoveOptionService moveOptionService;

    private Piece piece;
    private BoardMap board;
    private Game game;

    private int xPos;
    private int yPos;

    public void setMovableSquares(Piece piece, long gameId) {
        setup(piece, gameId);

        setKingBasicMoves();
        setKingCastlingMoves();
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        getPosition(piece);
        this.board = board;
        setKingBasicMoves();
    }

    public void setup(Piece piece, long gameId) {
        this.piece = piece;
        board = boardService.getBoardMapForGame(gameId);
        game = gameService.getGame(gameId);
        getPosition(piece);
    }

    private void getPosition(Piece piece) {
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();
    }

    public void setKingBasicMoves(){
        for (int x = xPos-1; x <= xPos+1; x++) {
            for (int y = yPos-1; y <= yPos+1; y++) {
                if (moveOptionService.withinBoard(x, y)) {
                    MoveOptionService.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
                }
            }
        }
    }

    // TODO: is king in check should be moved to LegalMoveService!
    public void setKingCastlingMoves() {
        King king = (King) piece;
        if (king.isInCheck() || king.isHasMoved())
            return;

        if (canCastleShort())
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));

        if (canCastleLong()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
        }
    }

    private boolean canCastleShort() {
        return board.getPieceByPos(xPos+1,yPos) == null &&
                kingIsSafeAt(board.getCoordinateByPos(xPos + 1, yPos)) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    private boolean canCastleLong() {
        return board.getPieceByPos(xPos-1, yPos) == null &&
                kingIsSafeAt(board.getCoordinateByPos(xPos - 1, yPos)) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    public boolean kingIsSafeAt(Coordinate position) {
        Player attackingPlayer = AllUtil.getOpponentPlayer(game, piece.getPlayer());
        playerService.setAllAttackedAndMovableSquaresForPlayer(attackingPlayer);
        List<Coordinate> attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        return !attackedSquares.contains(position);
    }
}
