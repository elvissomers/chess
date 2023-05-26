package main.classes.structures;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.pieces.*;

import java.util.HashMap;

public class BoardMap extends HashMap<Coordinate, Piece> {

    private Game game;

    /**
     * Constructor for BoardMap is supposed to generate a starting Board according to the standard chess rules.
     *
     * @param game the game the Board belongs to
     */
    public BoardMap(Game game){
        // Hardcoded for now, since game setup is defined for 8x8 board anyways
        int horizontalSize = 8;
        int verticalSize = 8;
        // Set all squares, empty
        for (Player player : new Player[] {game.getWhitePlayer(), game.getBlackPlayer()}){
            // do some stuff
            Piece[] piecesInOrder = new Piece[] {
                    new Rook(player), new Knight(player), new Bishop(player),
                    new Queen(player), new King(player), new Bishop(player),
                    new Knight(player), new Rook(player)
            };
            int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
            int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

            // Set major pieces
            for (int xPos = 0; xPos < horizontalSize; xPos++) {
                Piece piece = piecesInOrder[xPos];
                Coordinate pieceCoordinate = new Coordinate(xPos, yForMajorPieces);
                put(pieceCoordinate, piece);

//                currentPlayer.getPieceSet().add(piece);
            }

            // Set pawns
            for (int xPos = 0; xPos < horizontalSize; xPos++){
                Pawn pawn = new Pawn(player);
                Coordinate pieceCoordinate = new Coordinate(xPos, yForPawns);
                put(pieceCoordinate, pawn);

//                currentPlayer.getPieceSet().add(pawn);
            }

            // Set empty squares
            for (int xPos = 0; xPos < horizontalSize; xPos++){
                for (int yPos = 2; yPos < 6; yPos++){
                    Coordinate emptySquareCoordinate = new Coordinate(xPos, yPos);
                    put(emptySquareCoordinate, null);
                }
            }
        }
    }
}
