package com.practice.project.chess.repository.filling;

import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FillPieceRepositoryService {

    private final PieceRepository pieceRepository;

    private static final int[] boardRange = new int[]{0,1,2,3,4,5,6,7};
    private static final Team[] teams = new Team[]{Team.WHITE, Team.BLACK};
    private static final PieceType[] pieceTypes = new PieceType[]{PieceType.PAWN, PieceType.KNIGHT,
            PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.KING};
    private static final boolean[] hasMovedOptions = new boolean[]{true, false};

    public void fillPieceRepository() {
        if (isNotEmpty(pieceRepository))
            return;

        for (int x : boardRange) {
            for (int y : boardRange) {
                for (Team team : teams) {
                    for (PieceType pieceType : pieceTypes) {
                        createAndSavePiece(x, y, team, pieceType);
                    }
                }
            }
        }
    }

    private void createAndSavePiece(int x, int y, Team team, PieceType pieceType) {
        if (pieceType == PieceType.ROOK || pieceType == PieceType.KING) {
            for (boolean hasMoved : hasMovedOptions)
                createRookOrKing(x, y, team, pieceType, hasMoved);
        } else {
            createNewOtherPiece(x, y, team, pieceType);
        }
    }

    private void createRookOrKing(int x, int y, Team team, PieceType pieceType, boolean hasMoved) {
        PieceDao newPiece = PieceDao.builder()
                .horizontalPosition(x)
                .verticalPosition(y)
                .team(team)
                .pieceType(pieceType)
                .hasMoved(hasMoved)
                .build();
        pieceRepository.save(newPiece);
    }

    private void createNewOtherPiece(int x, int y, Team team, PieceType pieceType) {
        PieceDao newPiece = PieceDao.builder()
                .horizontalPosition(x)
                .verticalPosition(y)
                .team(team)
                .pieceType(pieceType)
                .build();
        pieceRepository.save(newPiece);
    }

    private boolean isNotEmpty(PieceRepository pieceRepository) {
        Optional<PieceDao> rookAtA1 = pieceRepository.findByHorizontalPositionAndVerticalPositionAndTeamAndPieceTypeAndHasMoved(
                0,0, Team.WHITE, PieceType.ROOK,false);
        return rookAtA1.isPresent();
    }
}
