package main.classes.movement;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

public class MoveMaker {

    // TODO: separate into more methods, so methods "do only one thing".
    public void movePiece(Game game, Piece piece, Coordinate destination) {
        Player thisPlayer = piece.getPlayer();
        Player enemyPlayer = (thisPlayer.getTeam() == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();

        Coordinate fromSquare = piece.getPosition();
        Move move = new Move(piece, fromSquare, destination);
        piece.setPosition(destination);

        // A piece is "taken" when an enemy piece moves to its square
        Piece takenPiece = game.getBoard().get(destination);
        if (takenPiece != null) {
            enemyPlayer.getPieces().remove(takenPiece);
        }

        if (piece instanceof King king && !king.isHasMoved()){
            if (destination.getX() - fromSquare.getX() == 2) {
                castle(game, king, (Rook) game.getBoard().getPieceByPos(7,fromSquare.getY()),
                        destination, CastleType.SHORT
                );
                return;
            } else if (destination.getX() - fromSquare.getX() == -2) {
                castle(game, king, (Rook) game.getBoard().getPieceByPos(0,fromSquare.getY()),
                        destination, CastleType.LONG
                );
                return;
            }
            king.setHasMoved(true);
        }

        if (piece instanceof Rook rook && !rook.isHasMoved()){
            rook.setHasMoved(true);
        }

        int promotionRank = (thisPlayer.getTeam() == Team.WHITE) ? 7 : 0;

        if (piece instanceof Pawn pawn && destination.getY() == promotionRank){
            thisPlayer.getPieces().remove(pawn);
            // TODO: let player choose a piece instead of always having a queen
            Queen promotionPiece = new Queen(thisPlayer);
            game.getBoard().put(destination, promotionPiece);
            thisPlayer.getPieces().add(promotionPiece);
            // TODO: add "promoted" attribute to Move object, with the piece specification
        } else {
            game.getBoard().put(destination, piece);
        }
        thisPlayer.getMoveHistory().add(move);
    }

    public void castle(Game game, King king, Rook rook, Coordinate destination, CastleType type){
        king.setHasMoved(true);
        rook.setHasMoved(true);

        game.getBoard().put(destination, king);

        int rookRelativePos = (type == CastleType.SHORT) ? -1 : 1;
        Coordinate rookPosition = game.getBoard().getCoordinateArray()
                [destination.getX()+rookRelativePos][destination.getY()];

        game.getBoard().put(rookPosition, rook);
        rook.setPosition(rookPosition);

        // TODO: save castling to move history
    }

    public void makeMove(Move move, Game game){
        // TODO
        if (move.getCastleType() != null)
            castle(); // TODO

        Player thisPlayer = (move.getPiece().getPlayer().getTeam() == Team.WHITE) ? game.getWhitePlayer() :
                game.getBlackPlayer();

        if (move.getTakenPiece() != null){
            game.getBoard().remove(move.getTakenPiece().getPosition());
            Player enemyPlayer = (thisPlayer.getTeam() == Team.WHITE) ? game.getBlackPlayer() :
                    game.getWhitePlayer();
            enemyPlayer.getPieces().remove(move.getTakenPiece());
        }

        if (move.isPromoted()){
            game.getBoard().remove(move.getPiece().getPosition());

            Queen queen = new Queen(move.getPiece().getPlayer());
            int yDirection = (move.getPiece().getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
            queen.setPosition(game.getBoard().getCoordinateByPos(move.getPiece().getPosition().getX(),
                    move.getPiece().getPosition().getY()+yDirection));
            game.getBoard().put(game.getBoard().getCoordinateByPos(move.getPiece().getPosition().getX(),
                    move.getPiece().getPosition().getY()+yDirection), queen);

            thisPlayer.getPieces().remove(move.getPiece());
            thisPlayer.getPieces().add(queen);
        } else {
            game.getBoard().put(move.getSquareFrom(), null);
            game.getBoard().put(move.getSquareTo(), move.getPiece());
            move.getPiece().setPosition(move.getSquareTo());
        }


    }
}
