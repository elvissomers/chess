package classes.pieces;

import classes.board.Square;

public class Knight extends Piece {

    public Knight(Team team) {
        super(team);
    }

    @Override
    public void setMoveableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
                int x1 = xPos + xDirection;
                int x2 = xPos + 2 * xDirection;
                int y1 = yPos + 2 * xDirection * yRelativeDirection;
                int y2 = yPos + xDirection * yRelativeDirection;

                if (0 <= x1 && x1 <= xSize && 0 <= y1 && y1 <= ySize){
                    Square currentSquare = this.getBoard().getSquareByPos(x1, y1);
                    if (currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                }
                if (0 <= x2 && x2 <= xSize && 0 <= y2 && y2 <= ySize){
                    Square currentSquare = this.getBoard().getSquareByPos(x2, y2);
                    if (currentSquare.getPiece().getTeam() != this.getTeam()) {
                        this.addMovableSquare(currentSquare);
                    }
                }
            }
        }
    }
}
