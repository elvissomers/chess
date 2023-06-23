package com.ordina.nl.chess.structures;

import com.ordina.nl.chess.movement.MoveFinder;
import com.ordina.nl.chess.pieces.Piece;

public enum MovementType {
    HORIZONTAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setHorizontalMoves(piece, board);
        }
    },
    VERTICAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setVerticalMoves(piece, board);
        }
    },
    DIAGONAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setDiagonalMoves(piece, board);
        }
    },
    LSHAPED {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setLShapedMoves(piece, board);
        }
    },
    PAWN {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setPawnMoves(piece, board);
        }
    },
    KING {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder) {
            moveFinder.setKingBasicMoves(piece, board);
            moveFinder.setKingCastlingMoves(piece, board);
        }
    };

    public abstract void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder);
}
