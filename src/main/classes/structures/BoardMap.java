package main.classes.structures;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.pieces.*;

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

    public Coordinate[][] getCoordinateArray() {
        return coordinateArray;
    }

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
     * "Empty" constructor that does not set pieces to the board
     */
    public BoardMap(Game game, Coordinate[][] coordinateArray){
        this.game = game;
        this.coordinateArray = coordinateArray;
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

            player.getPieces().add(piece);
            if (piece instanceof King king)
                player.setKing(king);
        }

        // Set pawns
        for (int xPos = 0; xPos < horizontalSize; xPos++){
            Pawn pawn = new Pawn(player);
            Coordinate pieceCoordinate = coordinateArray[xPos][yForPawns];
            put(pieceCoordinate, pawn);

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

}
