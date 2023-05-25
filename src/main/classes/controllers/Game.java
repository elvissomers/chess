package main.classes.controllers;

import main.classes.board.Board;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;

    private Player whitePlayer;

    private Player blackPlayer;

    private List<Move> moveHistory = new ArrayList<>();

    public Game() {
        board = new Board(8, 8);

        whitePlayer = new Player(this, Team.WHITE);
        new PieceSet(whitePlayer);
        blackPlayer = new Player(this, Team.BLACK);
        new PieceSet(blackPlayer);

        this.setStartBoard();
    }

    public Game(Game other) {
        this.board = new Board(other.getBoard());

        this.whitePlayer = new Player(other.getWhitePlayer());
        new PieceSet(whitePlayer);
        whitePlayer.setGame(this);
        this.blackPlayer = new Player(other.getBlackPlayer());
        new PieceSet(blackPlayer);
        blackPlayer.setGame(this);

        this.moveHistory = other.getMoveHistory();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public void setStartBoard(){
        /**
         * setStartBoard sets the Board to the standard chess starting position.
         *
         * It also fills the players PieceSets with all the pieces of their team.
         */
        for (Team team : new Team[] {Team.WHITE, Team.BLACK}) {
            Player currentPlayer = (team == Team.WHITE) ? whitePlayer : blackPlayer;

            Piece[] piecesInOrder = new Piece[] {
                    new Rook(this, null), new Knight(this, null), new Bishop(this, null),
                    new Queen(this, null), new King(this, null), new Bishop(this, null),
                    new Knight(this, null), new Rook(this, null)
            };
            int yForMajorPieces = (team == Team.WHITE) ? 0 : 7;
            int yForPawns = (team == Team.WHITE) ? 1 : 6;

            // Set major pieces
            for (int xPos = 0; xPos < piecesInOrder.length; xPos++) {
                Piece piece = piecesInOrder[xPos];
                piece.setTeam(team);
                piece.setSquare(this.board.getSquareByPos(xPos, yForMajorPieces));
                piece.setBoard(this.board);

                currentPlayer.getPieceSet().add(piece);
            }

            // Set pawns
            for (int xPos = 0; xPos < this.board.getHorizontalSize(); xPos++){
                Pawn pawn = new Pawn(this, team);
                pawn.setSquare(this.board.getSquareByPos(xPos, yForPawns));
                pawn.setBoard(this.board);

                currentPlayer.getPieceSet().add(pawn);
            }
        }
    }
}
