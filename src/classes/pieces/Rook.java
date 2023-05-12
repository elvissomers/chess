package classes.pieces;

public class Rook extends Piece {

    @Override
    public void setMoveableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

        for (int x = xPos; x < xSize; x++){
            if (this.getBoard().getSquares())
        }
    }
}
