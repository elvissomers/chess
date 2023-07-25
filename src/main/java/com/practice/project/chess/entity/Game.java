package com.practice.project.chess.entity;

import com.practice.project.chess.entity.pieces.*;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.service.MoveOptionService;
import com.ordina.nl.chess.entity.pieces.*;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.enums.GameState;
import com.practice.project.chess.enums.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState gameState;

    @OneToOne(mappedBy = "game")
    private Player whitePlayer;

    @OneToOne(mappedBy = "game")
    private Player blackPlayer;

    @Autowired
    private MoveOptionService moveFinder;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public void setStandardStartingGame() {
        int horizontalSize = 8;
        state = GameState.WHITE_TURN;
        whitePlayer = new Player(this, Team.WHITE); playerRepository.save(whitePlayer);
        blackPlayer = new Player(this, Team.BLACK); playerRepository.save(blackPlayer);

        for (Player player : Set.of(whitePlayer, blackPlayer)) {
            Piece[] piecesInOrder = new Piece[]{
                    new Rook(), new Knight(), new Bishop(), new Queen(), new King(),
                    new Bishop(), new Knight(), new Rook()
            };
            int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
            int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

            for (int xPos = 0; xPos < horizontalSize; xPos++) {
                Piece piece = piecesInOrder[xPos];
                piece.setHorizontalPosition(xPos); piece.setVerticalPosition(yForMajorPieces);

                piece.setPlayer(player); player.getPieces().add(piece);
                pieceRepository.save(piece);
            }

            for (int xPos = 0; xPos < horizontalSize; xPos++) {
                Pawn pawn = new Pawn();
                pawn.setHorizontalPosition(xPos); pawn.setVerticalPosition(yForPawns);

                pawn.setPlayer(player); player.getPieces().add(pawn);
                pieceRepository.save(pawn);
            }
        }
    }



    public void setMovableSquaresForPiece(Piece piece, BoardMap board) {
        moveFinder.setAllAttackedSquaresForEnemyPlayer(piece.getPlayer().getTeam(), board, this);

        piece.setMovableSquares(board);
        piece.setLegalMovableSquares();
    }

    public void checkState(Team teamTurn) {
        Player currentPlayer = (teamTurn == Team.WHITE) ? whitePlayer : blackPlayer;
        boolean canPlayerMove = currentPlayer.canMove();

        try {
            if (currentPlayer.getKing().isInCheck() && !canPlayerMove)
                state = (teamTurn == Team.WHITE) ? GameState.BLACK_WINS : GameState.WHITE_WINS;

            else if (!canPlayerMove && !currentPlayer.getKing().isInCheck())
                state = GameState.DRAW;

            else if (checkThreefoldRepetition() || checkFiftyMoveRule())
                state = GameState.DRAW;

            else
                state = (teamTurn == Team.WHITE) ? GameState.BLACK_TURN : GameState.WHITE_TURN;
        } catch (ElementNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private boolean checkThreefoldRepetition() {
        return hasThreefoldRepetition(whitePlayer) && hasThreefoldRepetition(blackPlayer);
    }

    private boolean checkFiftyMoveRule() {
        return checkFiftyMoveRuleForPlayer(whitePlayer) && checkFiftyMoveRuleForPlayer(blackPlayer);
    }

    // TODO!
    private boolean hasThreefoldRepetition(Player player) {
        int historySize = player.getMoveHistory().size();
        if (historySize < 3)
            return false;

        List<Move> lastThreeMoves = player.getLastNMoves(3);
        return false; // TODO!
    }

    private boolean checkFiftyMoveRuleForPlayer(Player player) {
        if (player.getMoveHistory().size() < 50)
            return false;

        List<Move> lastFiftyMoves = player.getLastNMoves(50);
        for (Move move : lastFiftyMoves) {
            if (move.getPiece() instanceof Pawn || move.getTakenPiece() != null) {
                return false;
            }
        }
        return true;
    }


}