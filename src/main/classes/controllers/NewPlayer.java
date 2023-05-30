package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.Coordinate;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player {

    private Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private Set<Piece> pieces;

    private Set<Square> attackedSquares;

    private King king;

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Team getTeam() {
        return team;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public King getKing() {
        return king;
    }

    public Set<Square> getAttackedSquares() {
        return attackedSquares;
    }

    // TODO: implement this using coordinate, instead of Square
    public void movePiece(Piece piece, Coordinate destination) {
        // A piece is "taken" when an enemy piece moves to its square
        Piece takenPiece = game.getBoard().get(destination); // Should be BoardMap
        if (takenPiece != null) {
            Player enemyPlayer = (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
            enemyPlayer.getPieces().remove(takenPiece);
        }
        // We removed it from the piece set. So what?

        // TODO: new Move object
        Square fromSquare = piece.getSquare();
        Move move = new Move(piece, fromSquare, destSquare);


        if (piece instanceof King king && !king.isHasMoved()){
            if (destSquare.getHorizontalPosition() - fromSquare.getHorizontalPosition() == 2) {
                castle(king, (Rook) game.getBoard().getSquareByPos(game.getBoard().getHorizontalSize()-1,
                        fromSquare.getVerticalPosition()).getPiece(), CastleType.SHORT
                );
                return;
            } else if (destSquare.getHorizontalPosition() - fromSquare.getHorizontalPosition() == -2) {
                castle(king, (Rook) game.getBoard().getSquareByPos(0,
                        fromSquare.getVerticalPosition()).getPiece(), CastleType.LONG
                );
                return;
            }
            king.setHasMoved(true);
        }
        if (piece instanceof Rook rook && !rook.isHasMoved()){
            rook.setHasMoved(true);
        }

        if (piece instanceof Pawn pawn && destSquare.getVerticalPosition() == game.getBoard().getVerticalSize()){
            pieceSet.remove(pawn);
            // TODO: let player choose a piece instead of always having a queen
            Queen promotionPiece = new Queen(game, team);
            promotionPiece.setSquare(destSquare);
            pieceSet.add(promotionPiece);
            // TODO: add "promoted" attribute to Move object, with the piece specification
        } else {
            piece.setSquare(destSquare);
        }
        moveHistory.add(move);
    }
}