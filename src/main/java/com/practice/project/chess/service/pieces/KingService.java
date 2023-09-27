package com.practice.project.chess.service.pieces;

import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.entity.pieces.Rook;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.GameService;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.service.PlayerService;
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
                    moveOptionService.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
                }
            }
        }
    }

    public boolean isInCheck() throws ElementNotFoundException {
        // TODO: use this for copied king as well?
        Player attackingPlayer = (piece.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer() :
                game.getWhitePlayer();
        playerService.setAllAttackedAndMovableSquaresForPlayer(attackingPlayer);
        SquaresDto attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        return kingIsInSquares(attackedSquares);
    }

    public boolean isInCheck(Coordinate position) {
        Player attackingPlayer = (piece.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer() :
                game.getWhitePlayer();
        playerService.setAllAttackedAndMovableSquaresForPlayer(attackingPlayer);
        SquaresDto attackedSquares = playerService.getAllAttackedSquaresForPlayer(attackingPlayer);
        return attackedSquares.getSquares().contains(position);
    }

    private boolean kingIsInSquares(SquaresDto squaresDto) {
        List<Coordinate> squares = squaresDto.getSquares();
        for (Coordinate square : squares) {
            if (square.getXPos() == xPos && square.getYPos() == yPos) {
                return true;
            }
        }
        return false;
    }

    public void setKingCastlingMoves() {
        King king = (King) piece;
        if (isInCheck() || king.isHasMoved())
            return;

        if (canCastleShort())
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));

        if (canCastleLong()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
        }
    }

    private boolean canCastleShort() {
        return board.getPieceByPos(xPos+1,yPos) == null &&
                !isInCheck(board.getCoordinateByPos(xPos+1, yPos)) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }

    private boolean canCastleLong() {
        return board.getPieceByPos(xPos-1, yPos) == null &&
                !isInCheck(board.getCoordinateByPos(xPos-1, yPos)) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved();
    }
}
