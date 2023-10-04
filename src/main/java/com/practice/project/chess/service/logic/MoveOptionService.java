package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
// TODO: xPos and yPos are not final, is het een probleem dat deze service dat wel is
// TODO andere classes waar deze ge inject wordt?
public class MoveOptionService {
    
    private int xPos;
    private int yPos;
    private final int[] directions = {-1, 1};

    public void addHorizontalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int direction : directions) {
            addHorizontalMovesForDirection(direction, piece, board);
        }
    }

    private void addHorizontalMovesForDirection(int direction, Piece piece, BoardMap board) {
        int x = xPos + direction;
        while (withinBoard(x, yPos)) {
            addMovableSquareIfEmptyOrEnemy(x, yPos, piece, board);
            if (hasPiece(x, yPos, board))
                break;
            x += direction;
        }
    }

    public void addVerticalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int direction : directions) {
            addVerticalMovesForDirection(direction, piece, board);
        }
    }

    private void addVerticalMovesForDirection(int direction, Piece piece, BoardMap board) {
        int y = yPos + direction;
        while (withinBoard(xPos, y)) {
            addMovableSquareIfEmptyOrEnemy(xPos, y, piece, board);
            if (hasPiece(xPos, y, board))
                break;
            y += direction;
        }
    }

    public void addDiagonalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int xDirection : directions) {
            for (int yDirection : directions) {
                addDiagonalMovesForDirection(xDirection, yDirection, piece, board);
            }
        }
    }

    private void getPosition(Piece piece) {
        xPos = piece.getCoordinate().getXPos();
        yPos = piece.getCoordinate().getYPos();
    }
    
    private void addDiagonalMovesForDirection(int xDirection, int yDirection, Piece piece, BoardMap board) {
        int x = xPos + xDirection;
        int y = yPos + yDirection;
        
        while (withinBoard(x, y)) {
            addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
            if (hasPiece(x, y, board))
                break;
            x += xDirection;
            y += yDirection;
        }
    }

    public boolean withinBoard(int x, int y) {
        return (x >= 0 && x < BoardSize.horizontalSize) && (y >= 0 && y < BoardSize.verticalSize);
    }

    public static void addMovableSquareIfEmptyOrEnemy(int x, int y, Piece piece, BoardMap board) {
        Piece otherPiece = board.getPieceByPos(x, y);
        if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
            piece.addMovableSquare(board.getCoordinateByPos(x, y));
        }
    }
    
    private static boolean hasPiece(int x, int y, BoardMap board) {
        return (board.getPieceByPos(x, y) != null);
    }
}
