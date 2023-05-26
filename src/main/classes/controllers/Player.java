package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
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

    public Player (Player other) {
        this.team = other.getTeam();
        this.game = null;
        this.moveHistory = other.getMoveHistory();
        // Null because pieceSet is designated to Player in PieceSet(Player) constructor
        this.pieceSet = null;
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
        // TODO: a piece is "taken" when an enemy piece moves to its square
        // How do we implement this?
        Piece takenPiece = destSquare.getPiece();
        Player enemyPlayer = (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
        enemyPlayer.getPieceSet().remove(takenPiece);
        // We removed it from the piece set. So what?

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

    public void castle(King king, Rook rook, CastleType type){
        // TODO: simple move method that just updates piece and square;
        // will be called from here and from the current movePiece method
        king.setHasMoved(true);
        rook.setHasMoved(true);

        if (type == CastleType.SHORT){
            king.setSquare(game.getBoard().getSquareByPos(king.getSquare().getHorizontalPosition()+2,
                    king.getSquare().getVerticalPosition()
            ));
            rook.setSquare(game.getBoard().getSquareByPos(rook.getSquare().getHorizontalPosition()-2,
                    rook.getSquare().getVerticalPosition()
            ));
        } else {
            king.setSquare(game.getBoard().getSquareByPos(king.getSquare().getHorizontalPosition()-2,
                    king.getSquare().getVerticalPosition()
            ));
            rook.setSquare(game.getBoard().getSquareByPos(rook.getSquare().getHorizontalPosition()+3,
                    rook.getSquare().getVerticalPosition()));
        }
    }
}
