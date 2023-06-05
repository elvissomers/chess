package main.classes.instances;

import main.classes.game.Move;
import main.classes.movement.MoveFinder;
import main.classes.movement.MoveMaker;
import main.classes.pieces.King;
import main.classes.pieces.Pawn;
import main.classes.pieces.Piece;
import main.classes.structures.BoardMap;
import main.classes.structures.Coordinate;
import main.classes.structures.GameState;
import main.classes.structures.Team;

import java.util.List;

public class Game {

    private GameState state;

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
        state = GameState.ONGOING;
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

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void update(Team teamTurn) {
        // TODO: have this take a player in turn as input and use that to do less computation?
        whitePlayer.setAllAttackedAndMovableSquares();
        blackPlayer.setAllAttackedAndMovableSquares();

        whitePlayer.getKing().setInCheck();
        blackPlayer.getKing().setInCheck();
        moveFinder.pruneSelfCheckMoves(this, moveMaker);
        checkState(teamTurn);
    }

    public void checkState(Team teamTurn) {
        Player currentPlayer = (teamTurn == Team.WHITE) ? whitePlayer : blackPlayer;
        if (currentPlayer.getKing().isInCheck() && currentPlayer.getAllMovableSquares().isEmpty())
            state = (teamTurn == Team.WHITE) ? GameState.BLACKWINS : GameState.WHITEWINS;

        if (currentPlayer.getAllMovableSquares().isEmpty() && !currentPlayer.getKing().isInCheck())
            state = GameState.DRAW;

        if (checkThreefoldRepetition() || checkFiftyMoveRule())
            state = GameState.DRAW;
    }

    private boolean checkThreefoldRepetition() {
        return hasThreefoldRepetition(whitePlayer) && hasThreefoldRepetition(blackPlayer);
    }

    private boolean hasThreefoldRepetition(Player player) {
        int historySize = player.getMoveHistory().size();
        if (historySize < 3)
            return false;

        Move lastMove = player.getMoveHistory().get(historySize - 1);
        return lastMove.equals(player.getMoveHistory().get(historySize - 3))
                && lastMove.equals(player.getMoveHistory().get(historySize - 2));
    }

    private boolean checkFiftyMoveRule() {
        List<Move> whiteHistory = whitePlayer.getMoveHistory();
        List<Move> blackHistory = blackPlayer.getMoveHistory();
        if (blackHistory.size() < 50)
            return false;

        for (int i = 0; i < 50; i++) {
            Move whiteMove = whiteHistory.get(whiteHistory.size() - 1 - i);
            if (whiteMove.getPiece() instanceof Pawn || whiteMove.getTakenPiece() != null) {
                return false;
            }
            Move blackMove = blackHistory.get(blackHistory.size() - 1 - i);
            if (blackMove.getPiece() instanceof Pawn || blackMove.getTakenPiece() != null) {
                return false;
            }
        }
        return true;
    }


}
