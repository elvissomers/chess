package com.practice.project.chess.service.pieces;

import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.entity.pieces.Rook;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class KingService {

    private final BoardService boardService;
    private final MoveOptionService moveOptionService;

    private int xPos;
    private int yPos;

    public void setMovableSquares(Piece piece, long gameId) {
        getPosition(piece);
        BoardMap board = boardService.getBoardMapForGame(gameId);

        setKingBasicMoves(piece, board);
    }

    private void getPosition(Piece piece) {
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();
    }

    public void setKingBasicMoves(Piece piece, BoardMap board){
        for (int x = xPos-1; x <= xPos+1; x++) {
            for (int y = yPos-1; y <= yPos+1; y++) {
                if (moveOptionService.withinBoard(x, y)) {
                    moveOptionService.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
                }
            }
        }
    }

    public void setKingCastlingMoves(Piece piece, BoardMap board, Game game){
        King king = (King) piece;
        if (king.isInCheck() || king.isHasMoved()){
            return;
        }
        Team team = king.getPlayer().getTeam();

        if (board.getPieceByPos(xPos+1,yPos) == null &&
                !checkCheck(board.getCoordinateByPos(xPos+1, yPos), board, team, game) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));
        }

        if (board.getPieceByPos(xPos-1, yPos) == null &&
                !checkCheck(board.getCoordinateByPos(xPos-1, yPos), board, team, game) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
        }
    }

    public boolean checkCheck(Coordinate position, BoardMap board, Team team, Game game) {
        Player attackingPlayer = (team == Team.WHITE) ? game.getBlackPlayer() :
                game.getWhitePlayer();
        attackingPlayer.setAllAttackedSquares(board);
        Set<Coordinate> squaresUnderAttack = attackingPlayer.getAllAttackedSquares();
        return squaresUnderAttack.contains(position);
    }
}
