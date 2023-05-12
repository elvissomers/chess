package classes.pieces;

import classes.board.Square;

public class Rook extends Piece {

    @Override
    public void setMoveableSquares() {
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
            for (int y = yPos + direction; (direction > 0 ? y < xSize : y > 0); y += direction) {
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

    }
}
