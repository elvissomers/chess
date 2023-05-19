package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.structures.Team;

public class Bishop extends Piece {

    public Bishop(Game game, Team team) {
        super(game, team);
    }

    @Override
    public void setMovableSquares() {
        System.out.println("hallooohoo?");
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

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

//        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
//            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
//                for (int x = xPos + xDirection; (xDirection > 0 ? x < xSize : x > 0);
//                     x += xDirection) {
//                    for (int y = yPos + yRelativeDirection * xDirection;
//                         (xDirection * yRelativeDirection > 0 ? y < ySize : y > 0); y += yRelativeDirection * xDirection) {
//                        Square currentSquare = this.getBoard().getSquareByPos(x, y);
//                        if (currentSquare.getPiece() != null) {
//                            if (currentSquare.getPiece().getTeam() != this.getTeam()) {
//                                this.addMovableSquare(currentSquare);
//                            }
//                            break;
//                        }
//                        this.addMovableSquare(currentSquare);
//                    }
//                }
//            }
//        }
    }
}
