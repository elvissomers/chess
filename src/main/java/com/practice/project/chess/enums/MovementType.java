package com.practice.project.chess.enums;

import com.practice.project.chess.entity.Game;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.entity.pieces.Piece;

public enum MovementType {
    HORIZONTAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.addHorizontalMoves(piece, board);
        }
    },
    VERTICAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.addVerticalMoves(piece, board);
        }
    },
    DIAGONAL {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.addDiagonalMoves(piece, board);
        }
    },
    LSHAPED {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.setLShapedMoves(piece, board);
        }
    },
    PAWN {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.setPawnMoves(piece, board);
            moveFinder.setPawnEnPassantMoves(piece, board, game);
        }
    },
    KING {
        @Override
        public void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game) {
            moveFinder.setKingBasicMoves(piece, board, game);
            moveFinder.setKingCastlingMoves(piece, board, game);
        }
    };

    public abstract void setMoves(Piece piece, BoardMap board, MoveOptionService moveFinder, Game game);
}

