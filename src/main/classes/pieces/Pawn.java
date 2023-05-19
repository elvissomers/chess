package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.structures.Team;

import java.util.Set;

public class Pawn extends Piece{
    public Pawn(Game game, Team team) {
        super(game, team);
    }

    /*
     * Unlike any of the other pieces, the pawn has specific squares they attack,
     * without necessarily being able to move to these squares.
     * We need to keep track of these squares to be able to see if there are
     * checks, checkmates or stalemates.
     */
    private Set<Square> attackedSquares;

    @Override
    public void setMovableSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();
        int yDirection = (this.getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (this.getTeam() == Team.WHITE) ? 1 : 6;

        Square squareInFront = this.getBoard().getSquareByPos(xPos, yPos + yDirection);
        if (squareInFront.getPiece() == null) {
            this.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < xSize) {
            Square squareInFrontRight = this.getBoard().getSquareByPos(xPos + 1,
                    yPos + yDirection);
            if (squareInFrontRight.getPiece() != null &&
                    squareInFrontRight.getPiece().getTeam() != this.getTeam()) {
                this.addMovableSquare(squareInFrontRight);
            }
        }

        if (xPos > 0) {
            Square squareInFrontLeft = this.getBoard().getSquareByPos(xPos - 1,
                    yPos + yDirection);
            if (squareInFrontLeft.getPiece() != null &&
                    squareInFrontLeft.getPiece().getTeam() != this.getTeam()) {
                this.addMovableSquare(squareInFrontLeft);
            }
        }


        if (yPos == startPos){
            Square squareTwoInFront = this.getBoard().getSquareByPos(xPos, yPos + 2 * yDirection);
            if (squareTwoInFront.getPiece() == null) {
                this.addMovableSquare(squareTwoInFront);
            }
        }

        // TODO : en passant
        // TODO : promotion
    }

    public void setAttackedSquares() {
        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();
        int yDirection = (this.getTeam() == Team.WHITE) ? 1 : -1;

        if (xPos + 1 < xSize) {
            Square squareInFrontRight = this.getBoard().getSquareByPos(xPos + 1,
                    yPos + yDirection);

        }

        if (xPos > 0) {
            Square squareInFrontLeft = this.getBoard().getSquareByPos(xPos - 1,
                    yPos + yDirection);
            this.attackedSquares.add(squareInFrontLeft);
        }
    }
}
