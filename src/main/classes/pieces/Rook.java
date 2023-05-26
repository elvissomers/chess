package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.Team;

public class Rook extends Piece {

    // Rook keeps track of whether it has moved during this game
    // - needed for castling rule
    // TODO: implement this by looping through moveHistory
    // if hasMoved is true for both rooks OR for the king, there is no need
    // to do this anymore.
    // TODO: this should probably be implemented in the PieceSet class
    private boolean hasMoved;

    public Rook(Game game, Team team) {
        super(game, team);
    }

    public Rook(Piece other){
        super(other);
    }

    public Rook(Player player) { super(player); }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public void setMovableSquares() {
        removePreviousMovableSquares();

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

    }
}
