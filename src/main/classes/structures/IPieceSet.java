package main.classes.structures;

import main.classes.pieces.King;
import main.classes.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

public interface IPieceSet extends Set<Piece> {

    public void setAllAttackedSquares();

    public void setAllMovableSquares();

    public King getKing();


}
