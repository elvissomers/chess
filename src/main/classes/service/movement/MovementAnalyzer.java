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

    public Set<Square> getHorizontalMovableSquares(){
        Set<Square> movableSquares = new HashSet<>();

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int x = piece.getSquare().getHorizontalPosition() + direction;
                 (direction > 0 ? x < board.getHorizontalSize() : x > 0); x += direction) {
                Square currentSquare = board.getSquareByPos(x, piece.getSquare().getVerticalPosition());
                if (currentSquare.getPiece() != null) {
                    if (currentSquare.getPiece().getTeam() != piece.getTeam()) {
                        movableSquares.add(currentSquare);
                    }
                    break;
                }
                movableSquares.add(currentSquare);
            }
        }

        return movableSquares;
    }

    public Set<Square> getVerticalMovableSquares(){
        Set<Square> movableSquares = new HashSet<>();

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int y = piece.getSquare().getVerticalPosition() + direction;
                 (direction > 0 ? y < board.getVerticalSize() : y > 0); y += direction) {
                Square currentSquare = board.getSquareByPos(piece.getSquare().getHorizontalPosition(), y);
                if (currentSquare.getPiece() != null) {
                    if (currentSquare.getPiece().getTeam() != piece.getTeam()) {
                        movableSquares.add(currentSquare);
                    }
                    break;
                }
                movableSquares.add(currentSquare);
            }
        }

        return movableSquares;
    }

    public Set<Square> getLShapedMovableSquares(){
        Set<Square> movableSquares = new HashSet<>();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
                int x1 = xPos + xDirection;
                int x2 = xPos + 2 * xDirection;
                int y1 = yPos + 2 * xDirection * yRelativeDirection;
                int y2 = yPos + xDirection * yRelativeDirection;

                if (0 <= x1 && x1 < xSize && 0 <= y1 && y1 < ySize){
                    Square currentSquare = this.getBoard().getSquareByPos(x1, y1);
                    if (currentSquare.getPiece() == null ||
                            currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                }
                if (0 <= x2 && x2 < xSize && 0 <= y2 && y2 < ySize){
                    Square currentSquare = this.getBoard().getSquareByPos(x2, y2);
                    if (currentSquare.getPiece() == null ||
                            currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                }
            }
        }

        return movableSquares;
    }
}
