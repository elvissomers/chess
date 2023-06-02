package main.classes.movement;

import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.game.Move;
import main.classes.pieces.*;
import main.classes.structures.CastleType;
import main.classes.structures.Coordinate;
import main.classes.structures.Team;

public class MoveMaker {

    public Move getMove(Game game, Piece piece, Coordinate destination) {
        Coordinate fromSquare = piece.getPosition();
        CastleType castleType = null;

        // A piece is "taken" when an enemy piece moves to its square
        // TODO: taken Piece in case of en PAssant:!
        Piece takenPiece = game.getBoard().get(destination);

        if (piece instanceof King king && !king.isHasMoved()){
            if (destination.getX() - fromSquare.getX() == 2)
                castleType = CastleType.SHORT;
            else if (destination.getX() - fromSquare.getX() == -2)
                castleType = CastleType.LONG;
        }

        Player thisPlayer = piece.getPlayer();
        int promotionRank = (thisPlayer.getTeam() == Team.WHITE) ? 7 : 0;

        boolean promoted = piece instanceof Pawn && destination.getY() == promotionRank;

        return(new Move(piece, fromSquare, destination, takenPiece, castleType, promoted));
    }

    public void makeMove(Move move, Game game){
        // TODO
        if (move.getCastleType() != null)
            castle(); // TODO
        if (move.getPiece() instanceof Rook rook && !rook.isHasMoved()){
            rook.setHasMoved(true);
        }
        if (move.getPiece() instanceof King king && !king.isHasMoved()){
            king.setHasMoved(true);
        }

        Player thisPlayer = (move.getPiece().getPlayer().getTeam() == Team.WHITE) ? game.getWhitePlayer() :
                game.getBlackPlayer();

        if (move.getTakenPiece() != null){
            game.getBoard().remove(move.getTakenPiece().getPosition());
            Player enemyPlayer = (thisPlayer.getTeam() == Team.WHITE) ? game.getBlackPlayer() :
                    game.getWhitePlayer();
            enemyPlayer.getPieces().remove(move.getTakenPiece());
        }

        if (move.isPromoted()){
            promote(move.getPiece(), game, thisPlayer);
        } else {
            game.getBoard().put(move.getSquareFrom(), null);
            game.getBoard().put(move.getSquareTo(), move.getPiece());
            move.getPiece().setPosition(move.getSquareTo());
        }
    }

    public void promote(Piece piece, Game game, Player player){
        game.getBoard().remove(piece.getPosition());

        // TODO: player should be able to pick a piece, instead of always getting a queen!
        Queen queen = new Queen(player);
        int yDirection = (player.getTeam() == Team.WHITE) ? 1 : -1;
        queen.setPosition(game.getBoard().getCoordinateByPos(piece.getPosition().getX(),
                piece.getPosition().getY()+yDirection));
        game.getBoard().put(game.getBoard().getCoordinateByPos(piece.getPosition().getX(),
                piece.getPosition().getY()+yDirection), queen);

        player.getPieces().remove(piece);
        player.getPieces().add(queen);
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
    }
}
