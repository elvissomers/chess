package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoveOptionService {

    private final int[] directions = {-1, 1};

    public void addHorizontalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int direction : directions) {
            addHorizontalMovesForDirection(xPos, yPos, direction, piece, board);
        }
    }

    private void addHorizontalMovesForDirection(int xPos, int yPos, int direction, Piece piece, BoardMap board) {
        int x = xPos + direction;
        while (AllUtil.withinBoard(x, yPos)) {
            AllUtil.addMovableSquareIfEmptyOrEnemy(x, yPos, piece, board);
            if (AllUtil.hasPiece(x, yPos, board))
                break;
            x += direction;
        }
    }

    public void addVerticalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int direction : directions) {
            addVerticalMovesForDirection(xPos, yPos, direction, piece, board);
        }
    }

    private void addVerticalMovesForDirection(int xPos, int yPos, int direction, Piece piece, BoardMap board) {
        int y = yPos + direction;
        while (AllUtil.withinBoard(xPos, y)) {
            AllUtil.addMovableSquareIfEmptyOrEnemy(xPos, y, piece, board);
            if (AllUtil.hasPiece(xPos, y, board))
                break;
            y += direction;
        }
    }

    public void addDiagonalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int xDirection : directions) {
            for (int yDirection : directions) {
                addDiagonalMovesForDirection(xPos, yPos, xDirection, yDirection, piece, board);
            }
        }
    }
    
    private void addDiagonalMovesForDirection(int xPos, int yPos, int xDirection, int yDirection,
                                              Piece piece, BoardMap board) {
        int x = xPos + xDirection;
        int y = yPos + yDirection;
        
        while (AllUtil.withinBoard(x, y)) {
            AllUtil.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
            if (AllUtil.hasPiece(x, y, board))
                break;
            x += xDirection;
            y += yDirection;
        }
    }
}
