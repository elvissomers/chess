package main.java.classes.structures;

import main.java.classes.movement.MoveFinder;
import main.java.classes.pieces.Piece;

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

