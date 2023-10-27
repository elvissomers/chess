package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.structures.BoardMap;

public class MoveOptionUtil {

    private MoveOptionUtil() {

    }
    private final static int[] DIRECTIONS = {-1, 1};

    public static void addHorizontalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int direction : DIRECTIONS) {
            addHorizontalMovesForDirection(xPos, yPos, direction, piece, board);
        }
    }

    private static void addHorizontalMovesForDirection(int xPos, int yPos, int direction, Piece piece, BoardMap board) {
        int x = xPos + direction;
        while (AllUtil.withinBoard(x, yPos)) {
            AllUtil.addMovableSquareIfEmptyOrEnemy(x, yPos, piece, board);
            if (AllUtil.hasPiece(x, yPos, board))
                break;
            x += direction;
        }
    }

    public static void addVerticalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int direction : DIRECTIONS) {
            addVerticalMovesForDirection(xPos, yPos, direction, piece, board);
        }
    }

    private static void addVerticalMovesForDirection(int xPos, int yPos, int direction, Piece piece, BoardMap board) {
        int y = yPos + direction;
        while (AllUtil.withinBoard(xPos, y)) {
            AllUtil.addMovableSquareIfEmptyOrEnemy(xPos, y, piece, board);
            if (AllUtil.hasPiece(xPos, y, board))
                break;
            y += direction;
        }
    }

    public static void addDiagonalMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int xDirection : DIRECTIONS) {
            for (int yDirection : DIRECTIONS) {
                addDiagonalMovesForDirection(xPos, yPos, xDirection, yDirection, piece, board);
            }
        }
    }
    
    private static void addDiagonalMovesForDirection(int xPos, int yPos, int xDirection, int yDirection,
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
