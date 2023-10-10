package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.logic.game.util.MoveUtil;
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

    private final MoveMapper moveMapper;

    // TODO: after getting or creating a move, it should also be saved (in DAO form) to both MoveRepository and PlayerMoveRepository
    public Move getOrCreateMove(Piece piece, Coordinate destination, Piece takenPiece) {
        return moveRepository.findByPiece_IdAndTakenPiece_IdAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalToAndPromotedTo(
                piece.getId(), takenPiece.getId(), piece.getHorizontalPosition(), destination.getXPos(), piece.getVerticalPosition(),
                destination.getYPos()) // TODO: this should also have promotedTo? Or can we still set promotedTo into the DAO later? THis might be multiple moves, with different promotedtos otherwise!
                .map(moveMapper::daoToMove)
                .orElse(createMove(piece, destination, takenPiece));
    }

    private Move createMove(Piece piece, Coordinate destination, Piece takenPiece) {
        return Move.builder()
                .piece(piece)
                .horizontalFrom(piece.getHorizontalPosition())
                .verticalFrom(piece.getVerticalPosition())
                .horizontalTo(destination.getXPos())
                .verticalTo(destination.getYPos())
                .takenPiece(takenPiece)
                .build();
    }

    public void setTakenPieceIfEnPassant(Move move, long gameId) {
        if (MoveUtil.pawnMovedDiagonally(move) && move.getTakenPiece() == null) {
            Piece takenPiece = boardService.getBoardMapForGame(gameId)
                    .getPieceByPos(move.getHorizontalTo(), move.getVerticalFrom());
            move.setTakenPiece(takenPiece);
        }
    }
}
