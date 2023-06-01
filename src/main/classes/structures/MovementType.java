package main.classes.structures;

import main.classes.movement.MovementFinder;
import main.classes.pieces.Piece;

public enum MovementType {
    HORIZONTAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setHorizontalMoves(piece, board);
        }
    },
    VERTICAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setVerticalMoves(piece, board);
        }
    },
    DIAGONAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setDiagonalMoves(piece, board);
        }
    },
    LSHAPED {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setLShapedMoves(piece, board);
        }
    },
    PAWN {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setPawnMoves(piece, board);
        }
    },
    KING {
        @Override
        public void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setKingBasicMoves(piece, board);
            moveFinder.setKingCastlingMoves(piece, board);
        }
    };

    public abstract void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder);
}

