package com.practice.project.chess.service.logic;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.logic.game.util.MoveUtil;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.model.mapper.MoveMapper;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.MoveRepository;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MoveService {

    private final MoveRepository moveRepository;

    private final BoardService boardService;
    private final PieceService pieceService;

    // TODO: after getting or creating a move, it should also be saved (in DAO form) to both MoveRepository and PlayerMoveRepository
    public MoveDao getOrCreateMove(PieceDao piece, Coordinate destination, PieceDao takenPiece, PieceType promotedTo) {
        return moveRepository.findByPieceAndTakenPieceAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalToAndPromotedTo(
                piece, takenPiece, piece.getHorizontalPosition(), destination.getXPos(), piece.getVerticalPosition(),
                destination.getYPos(), promotedTo)
                .orElse(createMove(piece, destination, takenPiece));
    }

    private MoveDao createMove(PieceDao piece, Coordinate destination, PieceDao takenPiece) {
        MoveDao moveToSave = MoveDao.builder()
                .piece(piece)
                .horizontalFrom(piece.getHorizontalPosition())
                .verticalFrom(piece.getVerticalPosition())
                .horizontalTo(destination.getXPos())
                .verticalTo(destination.getYPos())
                .takenPiece(takenPiece)
                .build();
        return moveRepository.save(moveToSave);
    }

    public void setTakenPieceIfEnPassant(MoveDao move, long gameId) {
        if (MoveUtil.pawnMovedDiagonally(move) && move.getTakenPiece() == null) {
            Piece takenPiece = boardService.getBoardMapForGame(gameId)
                    .getPieceByPos(move.getHorizontalTo(), move.getVerticalFrom());
            PieceDao takenPieceDao = pieceService.getOriginalDaoOfPiece(takenPiece);
            move.setTakenPiece(takenPieceDao);
        }
    }
}
