package com.ordina.nl.chess.structures;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.instances.Player;
import com.ordina.nl.chess.pieces.*;

import java.util.HashMap;
import java.util.Set;

public class BoardMap extends HashMap<Coordinate, Piece> {

    private transient Game game;

    private final transient Coordinate[][] coordinateArray;

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

    public Game getGame() {
        return game;
    }

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
