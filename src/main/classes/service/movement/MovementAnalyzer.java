package main.classes.service.movement;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.pieces.Piece;
import main.classes.structures.Team;

import java.util.HashSet;
import java.util.Set;

public class MovementAnalyzer {

    private Board board;

    private Piece piece;

    public Set<Square> getDiagonalMovableSquares(){
        Set<Square> movableSquares = new HashSet<>();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yDirection = -1; yDirection <= 1; yDirection += 2) {
                int x = piece.getSquare().getHorizontalPosition() + xDirection;
                int y = piece.getSquare().getVerticalPosition() + yDirection;

                while ((x >= 0 && x < board.getHorizontalSize()) && (y >= 0 && y < board.getVerticalSize())) {
                    Square currentSquare = board.getSquareByPos(x, y);
                    if (currentSquare.getPiece() != null) {
                        if (currentSquare.getPiece().getTeam() != piece.getTeam()) {
                            movableSquares.add(currentSquare);
                        }
                        break;
                    }
                    movableSquares.add(currentSquare);
                    x += xDirection;
                    y += yDirection;
                }
            }
        }
        return movableSquares;
    }
}
