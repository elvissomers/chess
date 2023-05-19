package main.classes.structures;

import main.classes.board.Square;
import main.classes.controllers.Player;
import main.classes.pieces.King;
import main.classes.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

public class PieceSet extends HashSet<Piece> implements IPieceSet{

    private Player player;

    private Set<Square> attackedSquares;

    public PieceSet(Player player) {
        this.player = player;
        this.attackedSquares = new HashSet<>();
    }


    @Override
    public void setAllAttackedSquares() {

    }

    @Override
    public void setAllMovableSquares() {

    }

    @Override
    public King getKing() {
        for (Piece piece : this) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }
}
