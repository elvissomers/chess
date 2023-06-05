package main.classes.instances;

import main.classes.movement.MoveFinder;
import main.classes.movement.MoveMaker;
import main.classes.pieces.King;
import main.classes.pieces.Piece;
import main.classes.structures.BoardMap;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

public class Game {

    private final BoardMap board;

    private final Player whitePlayer;

    private final Player blackPlayer;

    private final MoveFinder moveFinder = new MoveFinder();

    private final MoveMaker moveMaker = new MoveMaker();

    public Game() {
        whitePlayer = new Player(this, Team.WHITE);
        blackPlayer = new Player(this, Team.BLACK);

        board = new BoardMap(this);
    }

    /**
     * Copy constructor for Game. Should ensure everything is copied properly.
     * @param other the Game to copy.
     */
    public Game(Game other){
        whitePlayer = new Player(this, Team.WHITE);
        blackPlayer = new Player(this, Team.BLACK);
        Coordinate[][] coordinateArray = other.getBoard().getCoordinateArray(); // Reference, no copy
        board = new BoardMap(other, coordinateArray);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                Piece currentPiece = other.getBoard().get(coordinateArray[i][j]);
                Piece copyPiece = (currentPiece != null) ? currentPiece.copy() : null; // New instance, actual copy

                board.put(coordinateArray[i][j],copyPiece);
                if (copyPiece != null) {
                    Player player = (copyPiece.getPlayer().getTeam() == Team.WHITE) ? whitePlayer : blackPlayer;
                    copyPiece.setPlayer(player);
                    player.getPieces().add(copyPiece);
                    if (copyPiece instanceof King king)
                        player.setKing(king);
                }
            }
        }
    }

    public BoardMap getBoard() {
        return board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public MoveMaker getMoveMaker() {
        return moveMaker;
    }

    public MoveFinder getMoveFinder() {
        return moveFinder;
    }

    public void update() {
        whitePlayer.setAllAttackedSquares();
        blackPlayer.setAllAttackedSquares();

        whitePlayer.getKing().setInCheck();
        blackPlayer.getKing().setInCheck();
        moveFinder.pruneSelfCheckMoves(this, moveMaker);
    }
}
