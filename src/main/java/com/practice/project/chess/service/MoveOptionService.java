package com.practice.project.chess.service;

import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.entity.Game;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.entity.pieces.Rook;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.constants.BoardSize;

import java.util.Set;

public class MoveOptionService {
    
    private int xPos;
    private int yPos;
    private final int[] directions = {-1, 1};

    public void addHorizontalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int direction : directions) {
            addHorizontalMovesForDirection(direction, piece, board);
        }
    }

    private void addHorizontalMovesForDirection(int direction, Piece piece, BoardMap board) {
        int x = xPos + direction;
        while (withinBoard(x, yPos)) {
            addMovableSquareIfEmptyOrEnemy(x, yPos, piece, board);
            if (hasPiece(x, yPos, board))
                break;
            x += direction;
        }
    }

    public void addVerticalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int direction : directions) {
            addVerticalMovesForDirection(direction, piece, board);
        }
    }

    private void addVerticalMovesForDirection(int direction, Piece piece, BoardMap board) {
        int y = yPos + direction;
        while (withinBoard(xPos, y)) {
            addMovableSquareIfEmptyOrEnemy(xPos, y, piece, board);
            if (hasPiece(xPos, y, board))
                break;
            y += direction;
        }
    }

    public void addDiagonalMoves(Piece piece, BoardMap board){
        getPosition(piece);

        for (int xDirection : directions) {
            for (int yDirection : directions) {
                addDiagonalMovesForDirection(xDirection, yDirection, piece, board);
            }
        }
    }

    private void getPosition(Piece piece) {
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();
    }
    
    private void addDiagonalMovesForDirection(int xDirection, int yDirection, Piece piece, BoardMap board) {
        int x = xPos + xDirection;
        int y = yPos + yDirection;
        
        while (withinBoard(x, y)) {
            addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
            if (hasPiece(x, y, board))
                break;
            x += xDirection;
            y += yDirection;
        }
    }

    public boolean withinBoard(int x, int y) {
        return (x >= 0 && x < BoardSize.horizontalSize) && (y >= 0 && y < BoardSize.verticalSize);
    }

    public void addMovableSquareIfEmptyOrEnemy(int x, int y, Piece piece, BoardMap board) {
        Piece otherPiece = board.getPieceByPos(x, y);
        if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
            piece.addMovableSquare(board.getCoordinateByPos(x, y));
        }
    }
    
    private boolean hasPiece(int x, int y, BoardMap board) {
        return (board.getPieceByPos(x, y) != null);
    }

    public void setLShapedMoves(Piece piece, BoardMap board){
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();

        for (int xDirection : directions) {
            for (int yRelativeDirection : directions) {
                int x1 = xPos + xDirection;
                int x2 = xPos + 2 * xDirection;
                int y1 = yPos + 2 * xDirection * yRelativeDirection;
                int y2 = yPos + xDirection * yRelativeDirection;

                if (0 <= x1 && x1 < BoardSize.horizontalSize && 0 <= y1 && y1 < BoardSize.verticalSize){
                    Piece otherPiece = board.getPieceByPos(x1, y1);
                    if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[x1][y1]);
                    }
                }
                if (0 <= x2 && x2 < BoardSize.horizontalSize && 0 <= y2 && y2 < BoardSize.verticalSize){
                    Piece otherPiece = board.getPieceByPos(x2, y2);
                    if (otherPiece == null || otherPiece.getPlayer().getTeam() != piece.getPlayer().getTeam()) {
                        piece.addMovableSquare(board.getCoordinateArray()[x2][y2]);
                    }
                }
            }
        }
    }



    public void setPawnMoves(Piece pawn, BoardMap board) {
        xPos = pawn.getHorizontalPosition();
        yPos = pawn.getVerticalPosition();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        Coordinate squareInFront = board.getCoordinateArray()[xPos][yPos + yDirection];
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < BoardSize.horizontalSize) {
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
        xPos = pawn.getHorizontalPosition();
        yPos = pawn.getVerticalPosition();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.getPieceByPos(xPos - 1, yPos) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeEnPassawnedByPawn(otherPawn, (Pawn) pawn, game)) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < BoardSize.horizontalSize && board.getPieceByPos(xPos + 1, yPos) instanceof Pawn otherPawn &&
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
        xPos = king.getHorizontalPosition();
        yPos = king.getVerticalPosition();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (0 <= xPos + x && xPos + x < BoardSize.horizontalSize && 0 <= yPos + y && yPos + y < BoardSize.verticalSize) {
                    Piece currentPiece = board.getPieceByPos(xPos + x, yPos + y);
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

        xPos = king.getHorizontalPosition();
        yPos = king.getVerticalPosition();
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
            copyPiece.setHorizontalPosition(moveOption.getXPos());
            copyPiece.setVerticalPosition(moveOption.getYPos());

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
