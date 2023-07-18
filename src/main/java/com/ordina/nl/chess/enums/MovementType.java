package com.ordina.nl.chess.enums;

import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.MoveFinder;
import com.ordina.nl.chess.entity.pieces.Piece;

public enum MovementType {
    HORIZONTAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setHorizontalMoves(piece, board);
        }
    },
    VERTICAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setVerticalMoves(piece, board);
        }
    },
    DIAGONAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setDiagonalMoves(piece, board);
        }
    },
    LSHAPED {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setLShapedMoves(piece, board);
        }
    },
    PAWN {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setPawnMoves(piece, board);
            moveFinder.setPawnEnPassantMoves(piece, board, game);
        }
    },
    KING {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game) {
            moveFinder.setKingBasicMoves(piece, board, game);
            moveFinder.setKingCastlingMoves(piece, board, game);
        }
    };

    public abstract void setMoves(Piece piece, BoardMap board, MoveFinder moveFinder, Game game);
}

