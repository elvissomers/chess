package main.classes.structures;

import main.classes.controllers.Player;
import main.classes.pieces.*;

import java.util.HashMap;

public class PieceMap extends HashMap<PieceIdentifier, Piece> {

    private Player player;

    /**
     * PieceMap constructor is used to create all pieces of a player.
     *
     * @param player The player (Black/White) that this PieceMap belongs to.
     */
    public PieceMap(Player player){
        this.player = player;

        // TODO: what if the count of some piece type decreases? And increases again later?

        Queen queen = new Queen(player);
        put(new PieceIdentifier(PieceType.QUEEN, 0),queen);
        King king = new King(player);
        put(new PieceIdentifier(PieceType.KING, 0), king);

        int majorPiecesCount = 2;
        int pawnCount = 8;

        for (int i = 0; i < majorPiecesCount; i++){
            Knight knight = new Knight(player);
            put(new PieceIdentifier(PieceType.KNIGHT, i), knight);
            Bishop bishop = new Bishop(player);
            put(new PieceIdentifier(PieceType.BISHOP, i), bishop);
            Rook rook = new Rook(player);
            put(new PieceIdentifier(PieceType.ROOK, i), rook);
        }

        for (int i = 0; i < pawnCount; i++){
            Pawn pawn = new Pawn(player);
            put(new PieceIdentifier(PieceType.PAWN, i), pawn);
        }

    }

}
