package main.classes.structures;

import main.classes.controllers.Player;
import main.classes.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PieceMap extends HashMap<PieceIdentifier, Piece> {
    // Do we really need a full Map? We only ever need to specifically find the king,
    // or ALL pieces. We never need to specifically find a specific Pawn or Bishop or whatever
    // - only a specific piece on a specific square, which is already implemented in BoardMap.

    private Player player;

    private List<PieceIdentifier> pieceKeys = new ArrayList<>();

    /**
     * PieceMap constructor is used to create an EMPTY PieceMap. The keys are
     * set, but the pieces are only mapped to the entries when the Board is created.
     *
     * @param player The player (Black/White) that this PieceMap belongs to.
     */
    public PieceMap(Player player){
        this.player = player;

        // An ugly bit of code, but needed to get all the correct PieceIdentifiers.
    }


    /**
     * PieceMap constructor is used to create all pieces of a player.
     *
     * @param player The player (Black/White) that this PieceMap belongs to.
     */
    public PieceMap(Player player, int yo){
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
