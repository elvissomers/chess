package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.structures.BoardMap;
import main.classes.structures.Coordinate;
import main.classes.structures.MovementType;
import main.classes.structures.Team;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
    public Pawn(Game game, Team team) {
        super(game, team);
    }

    public Pawn(Piece other){
        super(other);
    }

    public Pawn(Player player){
        super(player);
        this.setMoveRules(Set.of(MovementType.PAWN));
    }

    /*
     * Unlike any of the other pieces, the pawn has specific squares they attack,
     * without necessarily being able to move to these squares.
     * We need to keep track of these squares to be able to see if there are
     * checks, checkmates or stalemates.
     */
    private Set<Coordinate> attackedSquares = new HashSet<>();
    // TODO : promotion


    public void setAttackedSquares(BoardMap board) {
        attackedSquares = new HashSet<>();

        int xPos = this.getPosition().getX();
        int yPos = this.getPosition().getY();
        int xSize = 8;
        int yDirection = (this.getTeam() == Team.WHITE) ? 1 : -1;

        if (xPos + 1 < xSize) {
            Coordinate squareInFrontRight = board.getCoordinateByPos(xPos + 1, yPos + yDirection);
            this.attackedSquares.add(squareInFrontRight);
        }

        if (xPos > 0) {
            Coordinate squareInFrontLeft = board.getCoordinateByPos(xPos - 1, yPos + yDirection);
            this.attackedSquares.add(squareInFrontLeft);
        }
    }

    public Set<Coordinate> getAttackedSquares() {
        return attackedSquares;
    }

    public Pawn copy(){
        return new Pawn(this);
    }
}
