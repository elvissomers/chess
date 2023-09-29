package com.practice.project.chess.service.gameservice.pieces;

import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KnightService {
    
    private final BoardService boardService;

    private int xPos;
    private int yPos;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        setMovableSquaresWithBoard(piece, board);
    }
    
    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        obtainPosition(piece);
        setLShapedMoves(piece, board);
    }

    private void obtainPosition(Piece piece) {
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();
    }

    public void setLShapedMoves(Piece piece, BoardMap board){
        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
                setLShapedMovesForDirection(xDirection, yRelativeDirection, piece, board);
            }
        }
    }

    private void setLShapedMovesForDirection(int xDirection, int yRelativeDirection, Piece piece, BoardMap board) {
        int x1 = xPos + xDirection;
        int x2 = xPos + 2 * xDirection;
        int y1 = yPos + 2 * xDirection * yRelativeDirection;
        int y2 = yPos + xDirection * yRelativeDirection;

        if (0 <= x1 && x1 < BoardSize.horizontalSize && 0 <= y1 && y1 < BoardSize.verticalSize){
            addMovableSquareIfEmptyOrEnemy(x1, y1, piece, board);
        }
        if (0 <= x2 && x2 < BoardSize.horizontalSize && 0 <= y2 && y2 < BoardSize.verticalSize){
            addMovableSquareIfEmptyOrEnemy(x2, y2, piece, board);
        }
    }
    
    private void addMovableSquareIfEmptyOrEnemy(int x, int y, Piece piece, BoardMap board) {
        Piece otherPiece = board.get(board.getCoordinateArray()[x][y]);
        if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
            piece.addMovableSquare(board.getCoordinateArray()[x][y]);
        }
    }
}
