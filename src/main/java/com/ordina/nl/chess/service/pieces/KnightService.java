package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.entity.pieces.Piece;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.service.BoardService;
import com.ordina.nl.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ordina.nl.chess.constants.BoardSize.horizontalSize;
import static com.ordina.nl.chess.constants.BoardSize.verticalSize;

@AllArgsConstructor
@Service
public class KnightService {
    
    private final BoardService boardService;

    private int xPos;
    private int yPos;

    public void setMovableSquares(Piece knight, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        obtainPosition(knight);
        
        setLShapedMoves(knight, board);
    }

    private void obtainPosition(Piece knight) {
        xPos = knight.getHorizontalPosition();
        yPos = knight.getVerticalPosition();
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

        if (0 <= x1 && x1 < horizontalSize && 0 <= y1 && y1 < verticalSize){
            addMovableSquareIfEmptyOrEnemy(x1, y1, piece, board);
        }
        if (0 <= x2 && x2 < horizontalSize && 0 <= y2 && y2 < verticalSize){
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
