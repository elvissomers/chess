package main.classes.structures;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class BoardMap extends HashMap<Coordinate, Piece> {
    /**
     * Note: BoardMap creates new Pieces. Therefore, these should not be
     * created in either Player or Game classes.
     *
     * Players are assumed to already exist when the BoardMap is created. Therefore,
     * players should be created before BoardMap. Their PieceMap, however, should still be
     * empty.
     */

    private Game game;

    private final Coordinate[][] coordinateArray;

    /**
     * Constructor for BoardMap is supposed to generate a starting Board according to the standard chess rules.
     *
     * @param game the game the Board belongs to
     */
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
     * Copy constructor to create a copy of a Board
     * @param other the Board to copy
     */
    public BoardMap(BoardMap other)  {
        // TODO: every Piece should be "deep" copied, but not necessarily every Coordinate. The coordinates used
        // are still the same
        coordinateArray = other.getCoordinateArray(); // Reference, no copy
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece currentPiece = other.get(coordinateArray[i][j]);
                Piece copyPiece = currentPiece.copy(); // New instance, actual copy
//                Class<? extends Piece> pieceClass = currentPiece.getType().getPieceImplementation();
//                Piece copyPiece = pieceClass.getDeclaredConstructor(pieceClass).newInstance(currentPiece);

                put(coordinateArray[i][j], copyPiece);
            }
        }
    }

    public Coordinate[][] getCoordinateArray() {
        return coordinateArray;
    }

    private void setStartPieces(Player player){
        int horizontalSize = 8;

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
            Coordinate pieceCoordinate = coordinateArray[xPos][yForMajorPieces];
            put(pieceCoordinate, piece);

//                currentPlayer.getPieceSet().add(piece);
        }

        // Set pawns
        for (int xPos = 0; xPos < horizontalSize; xPos++){
            Pawn pawn = new Pawn(player);
            Coordinate pieceCoordinate = coordinateArray[xPos][yForPawns];
            put(pieceCoordinate, pawn);

//                currentPlayer.getPieceSet().add(pawn);
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
}
