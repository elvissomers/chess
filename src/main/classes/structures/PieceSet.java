package main.classes.structures;

import main.classes.controllers.Player;
import main.classes.pieces.King;
import main.classes.pieces.Piece;

import java.util.Set;

public interface PieceSet extends Set<Piece> {

    Player player = null;

    public void setAllAttackedSquares();

    public void setAllMovableSquares();

    public King getKing();


}
