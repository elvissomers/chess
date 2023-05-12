package classes.pieces;

import classes.board.Square;

public class Bishop extends Piece {

    @Override
    public void setMoveableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yDirection = -1; yDirection <= 1; yDirection += 2) {
                for (int x = xPos + xDirection; (xDirection > 0 ? x < xSize : x > 0);
                     x += xDirection) {
                    for (int y = yPos + yDirection * xDirection;
                         (xDirection > 0 ? y < ySize : y > 0); y += yDirection * xDirection) {
                        Square currentSquare = this.getBoard().getSquareByPos(x, yPos);
                        if (currentSquare.getPiece() != null) {
                            // TODO: & is not king
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

    }
}
