package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;

public class Queen extends Piece{

    public Queen(Game game, Team team) {
        super(game, team);
    }

    @Override
    public void setMovableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int x = xPos + direction; (direction > 0 ? x < xSize : x > 0); x += direction) {
                Square currentSquare = this.getBoard().getSquareByPos(x, yPos);
                if (currentSquare.getPiece() != null) {
                    if (currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                    break;
                }
                this.addMovableSquare(currentSquare);
            }
        }

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int y = yPos + direction; (direction > 0 ? y < ySize : y > 0); y += direction) {
                Square currentSquare = this.getBoard().getSquareByPos(xPos, y);
                if (currentSquare.getPiece() != null) {
                    if (currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                    break;
                }
                this.addMovableSquare(currentSquare);
            }
        }

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yDirection = -1; yDirection <= 1; yDirection += 2) {
                int x = xPos + xDirection;
                int y = yPos + yDirection;

                while ((x >= 0 && x < xSize) && (y >= 0 && y < ySize)) {
                    Square currentSquare = this.getBoard().getSquareByPos(x, y);
                    if (currentSquare.getPiece() != null) {
                        if (currentSquare.getPiece().getTeam() != this.getTeam()) {
                            this.addMovableSquare(currentSquare);
                        }
                        break;
                    }
                    this.addMovableSquare(currentSquare);
                    x += xDirection;
                    y += yDirection;
                }
            }
        }
    }
}
