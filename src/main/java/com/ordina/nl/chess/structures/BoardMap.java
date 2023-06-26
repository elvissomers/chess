package com.ordina.nl.chess.structures;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.pieces.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Set;

public class BoardMap extends HashMap<Coordinate, Piece> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private transient Game game;

    private transient Coordinate[][] coordinateArray;

    public Coordinate[][] getCoordinateArray() {
        return coordinateArray;
    }

    public BoardMap(){
        coordinateArray = new Coordinate[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coordinateArray[i][j] = new Coordinate(i, j);
                put(coordinateArray[i][j], null);
            }
        }
    }

    public void setPiecesToBoard(Set<Piece> pieces) {
        for (Piece piece : pieces) {
            put(getCoordinateByPos(piece.getHorizontalPosition(), piece.getVerticalPosition()),
                    piece);
        }
    }

    public BoardMap(Game game){
        this.game = game;
        coordinateArray = new Coordinate[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coordinateArray[i][j] = new Coordinate(i, j);
            }
        }

        for (Player player : new Player[] {game.getWhitePlayer(), game.getBlackPlayer()})
            setStartPieces(player);

        setStartEmptySquares();
    }

    /**
     * "Empty" constructor that does not set pieces to the board
     */
    public BoardMap(Game game, Coordinate[][] coordinateArray){
        this.game = game;
        this.coordinateArray = coordinateArray;
    }

    private void setStartPieces(Player player){
        int horizontalSize = 8;

        Piece[] piecesInOrder = new Piece[] {
                new Rook(player), new Knight(player), new Bishop(player), new Queen(player),
                new King(player), new Bishop(player), new Knight(player), new Rook(player)
        };
        int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
        int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

        for (int xPos = 0; xPos < horizontalSize; xPos++) {
            Piece piece = piecesInOrder[xPos];
            Coordinate pieceCoordinate = coordinateArray[xPos][yForMajorPieces];
            put(pieceCoordinate, piece);
            piece.setPosition(pieceCoordinate);

            player.getPieces().add(piece);
            if (piece instanceof King king)
                player.setKing( king);
        }

        for (int xPos = 0; xPos < horizontalSize; xPos++){
            Pawn pawn = new Pawn(player);
            Coordinate pieceCoordinate = coordinateArray[xPos][yForPawns];
            put(pieceCoordinate, pawn);
            pawn.setPosition(pieceCoordinate);

            player.getPieces().add(pawn);
        }
    }

    private void setStartEmptySquares(){
        int horizontalSize = 8;

        for (int xPos = 0; xPos < horizontalSize; xPos++){
            for (int yPos = 2; yPos < 6; yPos++){
                Coordinate emptySquareCoordinate = coordinateArray[xPos][yPos];
                put(emptySquareCoordinate, null);
            }
        }
    }

    public Game getGame() {
        return game;
    }


    /**
     * Two short helper methods to replace the use of the clunky "board.get [(board.getCoordinateArray[x][y])]"
     * that is used so often with a shorter syntax.
     * @param xPos
     * @param yPos
     * @return board.get(board.getCoordinateArray[x][y]
     */
    public Piece getPieceByPos(int xPos, int yPos){
        return get(coordinateArray[xPos][yPos]);
    }
    public Coordinate getCoordinateByPos(int xPos, int yPos){
        return coordinateArray[xPos][yPos];
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder(" =================\n");
        for (int i = 0; i < 8; i++) {
            boardString.append((8 - i)).append("|");
            for (int j = 0; j < 8; j++) {
                Piece currentPiece = getPieceByPos(j, 7 - i);
                if (currentPiece == null)
                    boardString.append(" ");
                else
                    boardString.append((currentPiece instanceof Pawn) ? "1" : currentPiece.toString());
                boardString.append("|");
            }
            boardString.append("\n =================\n");
        }
        boardString.append(" |a|b|c|d|e|f|g|h|");
        return(boardString.toString());
    }
}
