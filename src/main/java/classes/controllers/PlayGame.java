package classes.controllers;

import classes.game.Move;
import classes.instances.Game;
import classes.pieces.Piece;
import classes.structures.Coordinate;
import classes.structures.Team;

import java.util.List;

public class PlayGame {

    private Team playerTurn;

    private Game game;

    @GetRequest
    private List<Coordinate> getMovableCoordinatesForPiece(int i, int j){
        return game.getBoard().getPieceByPos(i,j).getMovableSquares();
    }

    @PutRequest
    private void movePiece(Piece piece, Coordinate destination){
        Move move = game.getMoveMaker().getMove(game, piece, destination);
        game.getMoveMaker().makeMove(game, move);
    }

}
