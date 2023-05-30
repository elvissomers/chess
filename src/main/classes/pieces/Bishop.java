package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.Team;

public class Bishop extends Piece {

    public Bishop(Game game, Team team) {
        super(game, team);
    }

    // TODO change type from Piece to Bishop (idem) other classes
    public Bishop(Piece other){
        super(other);
    }

    public Bishop(Player player){
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

    public Bishop copy(){
        return new Bishop(this);
    }
}
