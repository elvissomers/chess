package com.practice.project.chess.enums;

import java.util.Set;

// TODO: replace all instanceof statements with type == PieceType.{}

public enum PieceType {
    PAWN {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.PAWN);
        }
    },
    KNIGHT {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.LSHAPED);
        }
    },
    BISHOP {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.DIAGONAL);
        }
    },
    ROOK {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL);
        }
    },
    QUEEN {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.HORIZONTAL, MovementType.VERTICAL, MovementType.DIAGONAL);
        }
    },
    KING {
        @Override
        public Set<MovementType> getMovementTypes() {
            return Set.of(MovementType.KING);
        }
    };

    public abstract Set<MovementType> getMovementTypes();
}
