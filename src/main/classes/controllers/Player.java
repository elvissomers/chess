package main.classes.controllers;

import main.classes.board.Square;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player {

    private Game game;

    private List<Move> moveHistory = new ArrayList<>();

    private final Team team;

    private Set<Piece> pieces;

    private Set<Coordinate> attackedSquares;

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

    public Set<Coordinate> getAttackedSquares() {
        return attackedSquares;
    }

    public void setAllMovableSquares() {
        for (Piece piece : pieces) {
            piece.setMovableSquares();
        }
    }

    public void setAllAttackedSquares() {
        for (Piece piece : this){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares();
                attackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares();
                attackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }

    // TODO: implement this using coordinate, instead of Square
    public void movePiece(Piece piece, Coordinate destination) {
        Coordinate fromSquare = piece.getPosition();
        piece.setPosition(destination);

        // A piece is "taken" when an enemy piece moves to its square
        Piece takenPiece = game.getBoard().get(destination); // Should be BoardMap
        if (takenPiece != null) {
            Player enemyPlayer = (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
            enemyPlayer.getPieces().remove(takenPiece);
        }

        // TODO: new Move object
//        Square fromSquare = piece.getSquare();
//        Move move = new Move(piece, fromSquare, destSquare);


        if (piece instanceof King king && !king.isHasMoved()){
            if (destination.getX() - fromSquare.getX() == 2) {
                castle(king, (Rook) game.getBoard().get(game.getBoard().getCoordinateArray()[7][fromSquare.getY()]),
                        destination, CastleType.SHORT
                );
                return;
            } else if (destination.getX() - fromSquare.getX() == -2) {
                castle(king, (Rook) game.getBoard().get(game.getBoard().getCoordinateArray()[0][fromSquare.getY()]),
                        destination, CastleType.LONG
                );
                return;
            }
            king.setHasMoved(true);
        }
        if (piece instanceof Rook rook && !rook.isHasMoved()){
            rook.setHasMoved(true);
        }

        int promotionRank = (team == Team.WHITE) ? 7 : 0;

        if (piece instanceof Pawn pawn && destination.getY() == promotionRank){
            pieces.remove(pawn);
            // TODO: let player choose a piece instead of always having a queen
            Queen promotionPiece = new Queen(game, team);
            game.getBoard().put(destination, promotionPiece);
            pieces.add(promotionPiece);
            // TODO: add "promoted" attribute to Move object, with the piece specification
        } else {
            game.getBoard().put(destination, piece);
        }
//        moveHistory.add(move);
    }

    public void castle(King king, Rook rook, Coordinate destination, CastleType type){
        // TODO: simple move method that just updates piece and square;
        // will be called from here and from the current movePiece method
        king.setHasMoved(true);
        rook.setHasMoved(true);

        game.getBoard().put(destination, king);

        int rookRelativePos = (type == CastleType.SHORT) ? -1 : 1;
        Coordinate rookPosition = game.getBoard().getCoordinateArray()
                [destination.getX()+rookRelativePos][destination.getY()];

        game.getBoard().put(rookPosition, rook);
        rook.setPosition(rookPosition);
    }
}