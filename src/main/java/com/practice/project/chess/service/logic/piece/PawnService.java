package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.service.model.mapper.PieceMapper;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PawnService {

    private final PieceRepository pieceRepository;

    private final BoardService boardService;

    private final PieceMapper pieceMapper;

    public SquaresDto getAttackedSquares(long pieceId) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(pieceId)
                        .map(pieceMapper::daoToPiece)
                        .map(Piece::getAttackedSquares)
                        .orElseThrow(()-> new ElementNotFoundException("Piece not present in repository")))
                .build();
    }

    public void setMovableSquares(Piece pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);

        addSquareInFrontToMovableSquares(pawn, board);
        addStartingMoveToMovableSquares(pawn, board);
        addPieceTakingMovesToMovableSquares(pawn, board);
    }

    public void setAttackedSquares(Pawn pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();

        if (xPos + 1 < BoardSize.HORIZONTAL_SIZE)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos + 1, yPos + 1));
        if (xPos > 0)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos - 1, yPos + 1));
    }

    public void setAttackedSquaresWithBoard(Piece piece, BoardMap board) {
        Pawn pawn = (Pawn) piece;
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();

        if (xPos + 1 < BoardSize.HORIZONTAL_SIZE)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos + 1, yPos + 1));
        if (xPos > 0)
            pawn.addAttackedSquare(board.getCoordinateByPos(xPos - 1, yPos + 1));
    }

    private void addSquareInFrontToMovableSquares(Piece pawn, BoardMap board) {
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();
        int yDirection = (pawn.getTeam() == Team.WHITE) ? 1 : -1;

        Coordinate squareInFront = board.getCoordinateByPos(xPos, yPos + yDirection);
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }
    }

    private void addPieceTakingMovesToMovableSquares(Piece pawn, BoardMap board) {
        int xPos = pawn.getCoordinate().getXPos();

        if (xPos + 1 < BoardSize.HORIZONTAL_SIZE)
            addRightTakingMoveToMovableSquares(pawn, board);
        if (xPos > 0)
            addLeftTakingMoveToMovableSquares(pawn, board);
    }

    private void addRightTakingMoveToMovableSquares(Piece pawn, BoardMap board) {
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();
        int yDirection = (pawn.getTeam() == Team.WHITE) ? 1 : -1;

        Coordinate squareInFrontRight = board.getCoordinateByPos(xPos + 1,yPos + yDirection);
        if (board.get(squareInFrontRight) != null && board.get(squareInFrontRight).
                getPlayer().getTeam() != pawn.getTeam()) {
            pawn.addMovableSquare(squareInFrontRight);
        }
    }

    private void addLeftTakingMoveToMovableSquares(Piece pawn, BoardMap board) {
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();
        int yDirection = (pawn.getTeam() == Team.WHITE) ? 1 : -1;

        Coordinate squareInFrontLeft = board.getCoordinateByPos(xPos - 1,yPos + yDirection);
        if (board.get(squareInFrontLeft) != null && board.get(squareInFrontLeft).
                getPlayer().getTeam() != pawn.getTeam()) {
            pawn.addMovableSquare(squareInFrontLeft);
        }
    }

    private void addStartingMoveToMovableSquares(Piece pawn, BoardMap board) {
        int xPos = pawn.getCoordinate().getXPos();
        int yPos = pawn.getCoordinate().getYPos();
        int yDirection = (pawn.getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getTeam() == Team.WHITE) ? 1 : 6;

        if (yPos == startPos) {
            Coordinate squareTwoInFront = board.getCoordinateByPos(xPos,yPos + 2 * yDirection);
            if (board.get(squareTwoInFront) == null) {
                pawn.addMovableSquare(squareTwoInFront);
            }
        }
    }
}
