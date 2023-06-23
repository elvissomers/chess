package com.ordina.nl.chess.structures;

import classes.pieces.*;
import com.ordina.nl.chess.pieces.*;

public enum PieceType {
    PAWN(Pawn.class),
    KNIGHT(Knight.class),
    BISHOP(Bishop.class),
    ROOK(Rook.class),
    QUEEN(Queen.class),
    KING(King.class);

    private final Class<? extends Piece> pieceImplementation;

    PieceType(final Class<? extends Piece> pieceImplementation) {
        this.pieceImplementation = pieceImplementation;
    }

    public Class<? extends Piece> getPieceImplementation() {
        return pieceImplementation;
    }
}