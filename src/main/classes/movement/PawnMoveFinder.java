package main.classes.movement;

import main.classes.board.Square;
import main.classes.controllers.Player;
import main.classes.pieces.Pawn;
import main.classes.structures.BoardMap;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

public class PawnMoveFinder {

    public void setPawnMoves(Pawn pawn, BoardMap board) {
        pawn.removePreviousMovableSquares();

        int xPos = pawn.getPosition().getX();
        int yPos = pawn.getPosition().getY();
        int xSize = 8;
        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        Coordinate squareInFront = board.getCoordinateArray()[xPos][yPos+yDirection];
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < xSize) {
            Coordinate squareInFrontRight = board.getCoordinateArray()[xPos+1][yPos+yDirection];
            if (board.get(squareInFrontRight) != null && board.get(squareInFrontRight).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontRight);
            }
        }

        if (xPos > 0) {
            Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos-1][yPos+yDirection];
            if (board.get(squareInFrontLeft) != null && board.get(squareInFrontLeft).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontLeft);
            }
        }

        if (yPos == startPos) {
            Coordinate squareTwoInFront = board.getCoordinateArray()[xPos][yPos+2*yDirection];
            if (board.get(squareTwoInFront) == null) {
                pawn.addMovableSquare(squareTwoInFront);
            }
        }

        // TODO : en passant
        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && this.getBoard().getSquareByPos(xPos - 1, yPos).getPiece()
                    instanceof Pawn pawn && pawn.getTeam() != this.getTeam()) {
                // TODO: check if pawn just movedIm
                Player otherPlayer = (this.getTeam() == Team.WHITE) ? this.getGame().getBlackPlayer()
                        : this.getGame().getWhitePlayer();
                if (otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1).getPiece()
                        == pawn && otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1)
                        .getSquareFrom().getVerticalPosition() == ((this.getTeam() == Team.BLACK) ? 1 : 6)
                ) {
                    Square squareInFrontLeft = this.getBoard().getSquareByPos(xPos - 1,
                            yPos + yDirection);
                    this.addMovableSquare(squareInFrontLeft);
                }
            } if (xPos + 1 < xSize) {
                System.out.println( "helloo?");
                if (this.getBoard().getSquareByPos(xPos + 1, yPos).getPiece()
                        instanceof Pawn pawn) {
                    if (pawn.getTeam() != this.getTeam()) {
                        // TODO: check if pawn just movedIm
                        Player otherPlayer = (this.getTeam() == Team.WHITE) ? this.getGame().getBlackPlayer()
                                : this.getGame().getWhitePlayer();
                        if (otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1).getPiece()
                                == pawn && otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1)
                                .getSquareFrom().getVerticalPosition() == ((this.getTeam() == Team.BLACK) ? 1 : 6)
                        ) {
                            Square squareInFrontRight = this.getBoard().getSquareByPos(xPos + 1,
                                    yPos + yDirection);
                            this.addMovableSquare(squareInFrontRight);
                        }
                    }
                }
            }
        }


    }
}
