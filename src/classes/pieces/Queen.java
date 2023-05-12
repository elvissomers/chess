package classes.pieces;

import classes.board.Square;

public class Queen extends Piece{

    public Queen(Team team) {
        super(team);
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
            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
                for (int x = xPos + xDirection; (xDirection > 0 ? x < xSize : x > 0);
                     x += xDirection) {
                    for (int y = yPos + yRelativeDirection * xDirection;
                         (xDirection > 0 ? y < ySize : y > 0); y += yRelativeDirection * xDirection) {
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
            }
        }
    }
}
