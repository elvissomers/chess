package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.King;
import main.classes.pieces.Piece;
import main.classes.pieces.Rook;
import main.classes.structures.PieceSet;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private PieceSet pieceSet;

    public Player(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public PieceSet getPieceSet() {
        return pieceSet;
    }

    public void setPieceSet(PieceSet pieceSet) {
        this.pieceSet = pieceSet;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }


    public void movePiece(Piece piece, Square destSquare) {
        Square fromSquare = piece.getSquare();
        Move move = new Move(piece, fromSquare, destSquare);

        if (piece instanceof King king && !king.isHasMoved()){
            if (destSquare.getHorizontalPosition() - fromSquare.getHorizontalPosition() == 2) {
                castle(king, (Rook) game.getBoard().getSquareByPos(game.getBoard().getHorizontalSize(),
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

        piece.setSquare(destSquare);
        moveHistory.add(move);
        // TODO: remove the double movehistory attribute
        this.game.getMoveHistory().add(move);
    }
}
