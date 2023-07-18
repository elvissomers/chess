package com.ordina.nl.chess.service;

import com.ordina.nl.chess.exception.ElementNotFoundException;
import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.pieces.Rook;
import com.ordina.nl.chess.enums.Team;

import java.util.Set;

public class MoveFinder {

    private static final int xSize = 8;

    private static final int ySize = 8;

    public void setHorizontalMoves(Piece piece, BoardMap board){
        int xPos = piece.getHorizontalPosition();
        int yPos = piece.getVerticalPosition();

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
        int xPos = piece.getHorizontalPosition();
        int yPos = piece.getVerticalPosition();

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
        int xPos = piece.getHorizontalPosition();
        int yPos = piece.getVerticalPosition();

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

    public void setLShapedMoves(Piece piece, BoardMap board){
        int xPos = piece.getHorizontalPosition();
        int yPos = piece.getVerticalPosition();

        for (int xDirection = -1; xDirection <= 1; xDirection += 2) {
            for (int yRelativeDirection = -1; yRelativeDirection <= 1; yRelativeDirection += 2) {
                int x1 = xPos + xDirection;
                int x2 = xPos + 2 * xDirection;
                int y1 = yPos + 2 * xDirection * yRelativeDirection;
                int y2 = yPos + xDirection * yRelativeDirection;

                if (0 <= x1 && x1 < xSize && 0 <= y1 && y1 < ySize){
                    Piece otherPiece = board.get(board.getCoordinateArray()[x1][y1]);
                    if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[x1][y1]);
                    }
                }
                if (0 <= x2 && x2 < xSize && 0 <= y2 && y2 < ySize){
                    Piece otherPiece = board.get(board.getCoordinateArray()[x2][y2]);
                    if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[x2][y2]);
                    }
                }
            }
        }
    }



    public void setPawnMoves(Piece pawn, BoardMap board) {
        int xPos = pawn.getHorizontalPosition();
        int yPos = pawn.getVerticalPosition();

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
    }

    public void setPawnEnPassantMoves(Piece pawn, BoardMap board, Game game) {
        int xPos = pawn.getHorizontalPosition();
        int yPos = pawn.getVerticalPosition();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.get(board.getCoordinateArray()[xPos - 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeEnPassawnedByPawn(otherPawn, (Pawn) pawn, game)) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < xSize && board.get(board.getCoordinateArray()[xPos + 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeEnPassawnedByPawn(otherPawn, (Pawn) pawn, game)) {
                    {
                        Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
                        pawn.addMovableSquare(squareInFrontRight);
                    }
                }
            }
        }
    }

    public void setKingBasicMoves(Piece king, BoardMap board, Game game){
        int xPos = king.getHorizontalPosition();
        int yPos = king.getVerticalPosition();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (0 <= xPos + x && xPos + x < xSize && 0 <= yPos + y && yPos + y < ySize) {
                    Piece currentPiece = board.get(board.getCoordinateArray()[xPos + x][yPos + y]);
                    if (currentPiece == null || currentPiece.getPlayer().getTeam() != king.getPlayer().getTeam()) {
                        if (!checkCheck(board.getCoordinateByPos(xPos+x,yPos+y), board,
                                king.getPlayer().getTeam(), game)) {
                            king.addMovableSquare(board.getCoordinateByPos(xPos+x,yPos+y));
                        }
                    }
                }
            }
        }
    }

    public void setKingCastlingMoves(Piece piece, BoardMap board, Game game){
        King king = (King) piece;
        if (king.isInCheck() || king.isHasMoved()){
            return;
        }

        int xPos = king.getHorizontalPosition();
        int yPos = king.getVerticalPosition();
        Team team = king.getPlayer().getTeam();

        if (board.getPieceByPos(xPos+1,yPos) == null &&
                !checkCheck(board.getCoordinateByPos(xPos+1, yPos), board, team, game) &&
                board.getPieceByPos(xPos+2, yPos) == null &&
                board.getPieceByPos(xPos+3, yPos) instanceof Rook rook &&
                !rook.isHasMoved()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos+2, yPos));
        }

        // Long castling from here
        if (board.getPieceByPos(xPos-1, yPos) == null &&
                !checkCheck(board.getCoordinateByPos(xPos-1, yPos), board, team, game) &&
                board.getPieceByPos(xPos-2, yPos) == null &&
                board.getPieceByPos(xPos-3, yPos) == null &&
                board.getPieceByPos(xPos-4, yPos) instanceof Rook rook &&
                !rook.isHasMoved()) {
            king.addMovableSquare(board.getCoordinateByPos(xPos-2, yPos));
        }
    }

    public boolean checkCheck(Coordinate position, BoardMap board, Team team, Game game) {
        Player attackingPlayer = (team == Team.WHITE) ? game.getBlackPlayer() :
                game.getWhitePlayer();
        attackingPlayer.setAllAttackedSquares(board);
        Set<Coordinate> squaresUnderAttack = attackingPlayer.getAllAttackedSquares();
        return squaresUnderAttack.contains(position);
    }

    public void pruneSelfCheckMovesForPiece(Piece piece, Game game) {
        for (Coordinate moveOption : piece.getMovableSquares()){
            Piece copyPiece = piece.copy();
            copyPiece.setHorizontalPosition(moveOption.getX());
            copyPiece.setVerticalPosition(moveOption.getY());

            BoardMap copyBoard = setBoardMapForCopiedPiece(piece, copyPiece, game);
            setAllAttackedSquaresForEnemyPlayer(piece.getPlayer().getTeam(), copyBoard, game);
            try {
                Coordinate kingCoordinate = new Coordinate(piece.getPlayer().getKing().getHorizontalPosition(),
                        piece.getPlayer().getKing().getVerticalPosition());
                if (checkCheck(kingCoordinate, copyBoard, piece.getPlayer().getTeam(), game)) {
                    continue;
                }
            } catch (ElementNotFoundException exception) {
                exception.printStackTrace();
            }

            piece.getLegalMovableSquares().add(moveOption);
        }
    }

    public boolean pawnCanBeEnPassawnedByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player otherPlayer = (targetPawn.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        return (otherPlayer.getLastMove().getPiece() == targetPawn && otherPlayer.getLastMove().getVerticalFrom()
                == ((attackingPawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6));
    }

    public BoardMap setBoardMap(Game game) {
        BoardMap board = new BoardMap();
        for (Player player : Set.of(game.getWhitePlayer(), game.getBlackPlayer())) {
            board.setPiecesToBoard(player.getPieces());
        }
        return board;
    }
    
    public BoardMap setBoardMapForCopiedPiece(Piece originalPiece, Piece copyPiece, Game game) {
        BoardMap board = new BoardMap();
        for (Player player : Set.of(game.getWhitePlayer(), game.getBlackPlayer())) {
            board.setPiecesToBoard(player.getPieces());
        }
        board.put(board.getCoordinateByPos(originalPiece.getHorizontalPosition(), originalPiece.getVerticalPosition()), null);
        board.put(board.getCoordinateByPos(copyPiece.getHorizontalPosition(), copyPiece.getVerticalPosition()), copyPiece);
        
        return board;
    }
    
    public void setAllAttackedSquaresForEnemyPlayer(Team team, BoardMap board, Game game) {
        Player enemyPlayer = (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
        enemyPlayer.setAllAttackedSquares(board);
    }
}
