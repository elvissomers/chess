package main.classes.structures;

import main.classes.movement.MovementFinder;
import main.classes.pieces.Piece;

public enum MovementType {
    HORIZONTAL {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setHorizontalMoves(piece, board);
        }
    },
    VERTICAL {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setVerticalMoves(piece, board);
        }
    },
    DIAGONAL {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setDiagonalMoves(piece, board);
        }
    },
    LSHAPED {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setLShapedMoves(piece, board);
        }
    },
    PAWN {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setPawnMoves(piece, board);
        }
    },
    KING {
        @Override
        void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder) {
            moveFinder.setKingBasicMoves(piece, board);
            moveFinder.setKingCastlingMoves(piece, board);
        }
    };

    abstract void setMoves(Piece piece, BoardMap board, MovementFinder moveFinder);
}

