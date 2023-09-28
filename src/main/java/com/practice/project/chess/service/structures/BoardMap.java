package com.practice.project.chess.service.structures;

import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardMap extends HashMap<Coordinate, Piece> {

    private final transient Coordinate[][] coordinateArray;

    public void setPiecesToBoard(List<Piece> pieces) {
        for (Piece piece : pieces) {
            put(getCoordinateByPos(piece.getHorizontalPosition(), piece.getVerticalPosition()),
                    piece);
        }
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
