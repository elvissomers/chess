package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.movement.MoveFinder;
import com.ordina.nl.chess.movement.MoveMaker;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.structures.BoardMap;
import com.ordina.nl.chess.structures.Coordinate;
import com.ordina.nl.chess.structures.GameState;
import com.ordina.nl.chess.structures.Team;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    @Column(name = "state")
    private GameState state;

    @OneToOne(mappedBy = "game")
    private Player whitePlayer;

    @OneToOne(mappedBy = "game")
    private Player blackPlayer;

    // TODO : logic classes should be @Autowired objects in Controller instead of Object?
    @Autowired
    private MoveFinder moveFinder;

    @Autowired
    private MoveMaker moveMaker;

    // TODO : update all empty constructors
    public Game() {

    }
    public Game(int i) {
        whitePlayer = new Player(this, Team.WHITE);
        blackPlayer = new Player(this, Team.BLACK);
    }

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

    public long getId() {
        return id;
    }

    public BoardMap getBoard() {
        return board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
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

    public void setMovableSquaresForPiece(Piece piece) {
        BoardMap board = moveFinder.setBoardMap(this);
        moveFinder.setAllAttackedSquaresForEnemyPlayer(piece.getPlayer().getTeam(), board, this);

        piece.setMovableSquares(board);
        piece.setLegalMovableSquares();
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
