package main.classes.pieces;

import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
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


    public void setAttackedSquares() {
        attackedSquares = new HashSet<>();

        int xPos = this.getSquare().getHorizontalPosition();
        int yPos = this.getSquare().getVerticalPosition();
        int xSize = this.getBoard().getHorizontalSize();
        int ySize = this.getBoard().getVerticalSize();
        int yDirection = (this.getTeam() == Team.WHITE) ? 1 : -1;

        if (xPos + 1 < xSize) {
            Square squareInFrontRight = this.getBoard().getSquareByPos(xPos + 1, yPos + yDirection);
            this.attackedSquares.add(squareInFrontRight);
        }

        if (xPos > 0) {
            Square squareInFrontLeft = this.getBoard().getSquareByPos(xPos - 1, yPos + yDirection);
            this.attackedSquares.add(squareInFrontLeft);
        }
    }

    public Set<Square> getAttackedSquares() {
        return attackedSquares;
    }

    public Pawn copy(){
        return new Pawn(this);
    }
}
