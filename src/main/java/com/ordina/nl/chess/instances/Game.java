package com.ordina.nl.chess.instances;

import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.movement.MoveFinder;
import com.ordina.nl.chess.pieces.*;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.repository.PlayerRepository;
import com.ordina.nl.chess.structures.BoardMap;
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

    @Autowired
    private MoveFinder moveFinder;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // TODO : update all empty constructors
    public Game() {

    }

    public void setStandardStartingGame() {
        int horizontalSize = 8;
        state = GameState.WHITE_TURN;
        whitePlayer = new Player(this, Team.WHITE);
        playerRepository.save(whitePlayer);
        blackPlayer = new Player(this, Team.BLACK);
        playerRepository.save(blackPlayer);

        for (Player player : Set.of(whitePlayer, blackPlayer)) {
            Piece[] piecesInOrder = new Piece[]{
                    new Rook(), new Knight(), new Bishop(), new Queen(), new King(),
                    new Bishop(), new Knight(), new Rook()
            };
            int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
            int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

            for (int xPos = 0; xPos < horizontalSize; xPos++) {
                Piece piece = piecesInOrder[xPos];
                piece.setHorizontalPosition(xPos);
                piece.setVerticalPosition(yForMajorPieces);

                piece.setPlayer(player);
                player.getPieces().add(piece);
                pieceRepository.save(piece);
            }

            for (int xPos = 0; xPos < horizontalSize; xPos++) {
                Pawn pawn = new Pawn();
                pawn.setHorizontalPosition(xPos);
                pawn.setVerticalPosition(yForPawns);

                pawn.setPlayer(player);
                player.getPieces().add(pawn);
                pieceRepository.save(pawn);
            }
        }
    }

    public long getId() {
        return id;
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

    public void checkState(Team teamTurn) {
        Player currentPlayer = (teamTurn == Team.WHITE) ? whitePlayer : blackPlayer;
        if (currentPlayer.getKing().isInCheck() && currentPlayer.getAllMovableSquares().isEmpty())
            state = (teamTurn == Team.WHITE) ? GameState.BLACK_WINS : GameState.WHITE_WINS;

        else if (currentPlayer.getAllMovableSquares().isEmpty() && !currentPlayer.getKing().isInCheck())
            state = GameState.DRAW;

        else if (checkThreefoldRepetition() || checkFiftyMoveRule())
            state = GameState.DRAW;

        else
            state = (teamTurn == Team.WHITE) ? GameState.BLACK_TURN : GameState.WHITE_TURN;
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
