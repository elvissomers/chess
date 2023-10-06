package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.logic.game.util.MoveUtil;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.MoveRepository;
import com.practice.project.chess.repository.PlayerMoveRepository;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MoveService {
    // TODO: move should also be used as component only, and DAO should be used for obtaining from and saving to repository

    private final MoveRepository moveRepository;
    private final PlayerMoveRepository playerMoveRepository;

    private final BoardService boardService;

    public List<PlayerMove> getPlayerMoves(long playerId) {
        return playerMoveRepository.findByPlayer_Id(playerId);
    }

    public Move getOrCreateMove(Piece piece, Coordinate destination, Piece takenPiece) {
        return moveRepository.findByPieceAndTakenPieceAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(
                piece, takenPiece, piece.getHorizontalPosition(), destination.getXPos(), piece.getVerticalPosition(),
                destination.getYPos())
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
