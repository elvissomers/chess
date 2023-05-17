package main.classes.pieces;

import main.classes.board.Square;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    // King keeps track of whether it has moved during this game
    // - needed for castling rule
    private boolean hasMoved;

    private boolean inCheck;

    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public void setMovableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++){
                if (0 <= xPos + x && xPos + x <= xSize && 0 <= yPos + y && yPos + y <= ySize){
                Square currentSquare = this.getBoard().getSquareByPos(xPos + x, yPos + y);
                if (currentSquare.getPiece() == null ||
                        currentSquare.getPiece().getTeam() != this.getTeam()) {
                    // TODO: check if king is put in check by moving here
                    this.addMovableSquare(currentSquare);
                    }
                }
            }
        }

    }
}
