package main.classes.structures;

import main.classes.pieces.King;
import main.classes.pieces.Piece;

import java.util.Set;

public interface PieceSet extends Set<Piece> {

    King king = new King(null, null);

    public void setAllAttackedSquares();

    public void setAllMovableSquares();
}
