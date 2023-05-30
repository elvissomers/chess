package main.classes.movement;

import main.classes.board.Square;
import main.classes.controllers.Player;
import main.classes.pieces.Pawn;
import main.classes.pieces.Piece;
import main.classes.structures.BoardMap;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

public class MovementFinder {

    private final int xSize = 8;

    private final int ySize = 8;

    public void setHorizontalMoves(Piece piece, BoardMap board){
        piece.removePreviousMovableSquares();

        int xPos = piece.getPosition().getX();
        int yPos = piece.getPosition().getY();

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int x = xPos + direction; (direction > 0 ? x < xSize : x > 0); x += direction) {
                Piece possiblePiece = board.get(board.getCoordinateArray()[x][yPos]);
                if (possiblePiece != null) {
                    if (possiblePiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[x][yPos]);
                    }
                    break;
                }
                piece.addMovableSquare(board.getCoordinateArray()[x][yPos]);
            }
        }
    }

    public void setVerticalMoves(Piece piece, BoardMap board){
        piece.removePreviousMovableSquares();

        int xPos = piece.getPosition().getX();
        int yPos = piece.getPosition().getY();

        for (int direction = -1; direction <= 1; direction += 2) {
            for (int y = yPos + direction; (direction > 0 ? y < ySize : y > 0); y += direction) {
                Piece possiblePiece = board.get(board.getCoordinateArray()[xPos][y]);
                if (possiblePiece != null) {
                    if (possiblePiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[xPos][y]);
                    }
                    break;
                }
                piece.addMovableSquare(board.getCoordinateArray()[xPos][y]);
            }
        }
    }

    public void setDiagonalMoves(Piece piece, BoardMap board){
        piece.removePreviousMovableSquares();

        int xPos = piece.getPosition().getX();
        int yPos = piece.getPosition().getY();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yDirection = -1; yDirection <= 1; yDirection += 2) {
                int x = xPos + xDirection;
                int y = yPos + yDirection;

                while ((x >= 0 && x < xSize) && (y >= 0 && y < ySize)) {
                    Piece possiblePiece = board.get(board.getCoordinateArray()[x][y]);
                    if (possiblePiece != null) {
                        if (possiblePiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                            piece.addMovableSquare(board.getCoordinateArray()[x][y]);
                        }
                        break;
                    }
                    piece.addMovableSquare(board.getCoordinateArray()[x][y]);
                    x += xDirection;
                    y += yDirection;
                }
            }
        }
    }

    public void setPawnMoves(Piece pawn, BoardMap board) {
        pawn.removePreviousMovableSquares();

        int xPos = pawn.getPosition().getX();
        int yPos = pawn.getPosition().getY();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        Coordinate squareInFront = board.getCoordinateArray()[xPos][yPos + yDirection];
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < xSize) {
            Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
            if (board.get(squareInFrontRight) != null && board.get(squareInFrontRight).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontRight);
            }
        }

        if (xPos > 0) {
            Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
            if (board.get(squareInFrontLeft) != null && board.get(squareInFrontLeft).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontLeft);
            }
        }

        if (yPos == startPos) {
            Coordinate squareTwoInFront = board.getCoordinateArray()[xPos][yPos + 2 * yDirection];
            if (board.get(squareTwoInFront) == null) {
                pawn.addMovableSquare(squareTwoInFront);
            }
        }

        // TODO : en passant should also take the pawn!
        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.get(board.getCoordinateArray()[xPos - 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                // TODO: check if pawn just movedIm
                Player otherPlayer = (pawn.getPlayer().getTeam() == Team.WHITE) ? board.getGame().getBlackPlayer()
                        : board.getGame().getWhitePlayer();
                if (otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1).getPiece()
                        == otherPawn && otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1)
                        .getSquareFrom().getY() == ((pawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6)
                ) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < xSize && board.get(board.getCoordinateArray()[xPos + 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                // TODO: check if pawn just movedIm
                Player otherPlayer = (pawn.getPlayer().getTeam() == Team.WHITE) ? board.getGame().getBlackPlayer()
                        : board.getGame().getWhitePlayer();
                if (otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1).getPiece()
                        == otherPawn && otherPlayer.getMoveHistory().get(otherPlayer.getMoveHistory().size() - 1)
                        .getSquareFrom().getY() == ((pawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6)
                ) {
                    Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontRight);
                }
            }
        }


    }
}
