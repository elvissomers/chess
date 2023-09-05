package com.practice.project.chess.service.pieces;

import com.practice.project.chess.data.dto.PieceDto;
import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.GameService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PawnService {

    private final PieceRepository pieceRepository;

    private final GameService gameService;
    private final BoardService boardService;

    private int xPos;
    private int yPos;
    private int startPos;
    private int yDirection;

    public SquaresDto getAttackedSquares(long pieceId) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(pieceId)
                        .map(Piece::getAttackedSquares)
                        .orElse(null)) // TODO: .orElseThrow
                .build();
    }

    public void setMovableSquares(Piece pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        obtainPosition(pawn);

        addSquareInFrontToMovableSquares(pawn, board);
        addStartingMoveToMovableSquares(pawn, board);
        addPieceTakingMovesToMovableSquares(pawn, board);
        addPawnEnPassantMovesToMovableSquares(pawn, board, gameId);
    }

    public void setAttackedSquares(Pawn pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        obtainPosition(pawn);

        if (xPos + 1 < BoardSize.horizontalSize)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos + 1, yPos + 1));
        if (xPos > 0)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos - 1, yPos + 1));
    }

    public void setAttackedSquaresWithBoard(Piece piece, BoardMap board) {
        Pawn pawn = (Pawn) piece;
        obtainPosition(pawn);

        if (xPos + 1 < BoardSize.horizontalSize)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos + 1, yPos + 1));
        if (xPos > 0)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos - 1, yPos + 1));
    }

    private void obtainPosition(Piece pawn) {
        xPos = pawn.getHorizontalPosition();
        yPos = pawn.getVerticalPosition();
        yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;
    }

    private void addSquareInFrontToMovableSquares(Piece pawn, BoardMap board) {
        Coordinate squareInFront = board.getCoordinateByPos(xPos, yPos + yDirection);
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }
    }

    private void addPieceTakingMovesToMovableSquares(Piece pawn, BoardMap board) {
        if (xPos + 1 < BoardSize.horizontalSize)
            addRightTakingMoveToMovableSquares(pawn, board);
        if (xPos > 0)
            addLeftTakingMoveToMovableSquares(pawn, board);
    }

    private void addRightTakingMoveToMovableSquares(Piece pawn, BoardMap board) {
        Coordinate squareInFrontRight = board.getCoordinateByPos(xPos + 1,yPos + yDirection);
        if (board.get(squareInFrontRight) != null && board.get(squareInFrontRight).
                getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
            pawn.addMovableSquare(squareInFrontRight);
        }
    }

    private void addLeftTakingMoveToMovableSquares(Piece pawn, BoardMap board) {
        Coordinate squareInFrontLeft = board.getCoordinateByPos(xPos - 1,yPos + yDirection);
        if (board.get(squareInFrontLeft) != null && board.get(squareInFrontLeft).
                getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
            pawn.addMovableSquare(squareInFrontLeft);
        }
    }

    private void addStartingMoveToMovableSquares(Piece pawn, BoardMap board) {
        if (yPos == startPos) {
            Coordinate squareTwoInFront = board.getCoordinateByPos(xPos,yPos + 2 * yDirection);
            if (board.get(squareTwoInFront) == null) {
                pawn.addMovableSquare(squareTwoInFront);
            }
        }
    }

    public void addPawnEnPassantMovesToMovableSquares(Piece pawn, BoardMap board, long gameId) {
        Game game = gameService.getGame(gameId);

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.get(board.getCoordinateArray()[xPos - 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < BoardSize.horizontalSize && board.get(board.getCoordinateArray()[xPos + 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                    {
                        Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
                        pawn.addMovableSquare(squareInFrontRight);
                    }
                }
            }
        }
    }

    private boolean pawnCanBeTakenEnPassantByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player otherPlayer = (targetPawn.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        return (otherPlayer.getLastMove().getPiece() == targetPawn && otherPlayer.getLastMove().getVerticalFrom()
                == ((attackingPawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6));
    }
    
    
}
