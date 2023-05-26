package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.Team;

public class Knight extends Piece {

    public Knight(Game game, Team team) {
        super(game, team);
    }

    public Knight(Piece other){
        super(other);
    }

    public Knight(Player player){
        super(player);
    }

    @Override
    public void setMovableSquares() {
        removePreviousMovableSquares();

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
    }
}
