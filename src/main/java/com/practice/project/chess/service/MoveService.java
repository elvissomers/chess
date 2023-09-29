package com.practice.project.chess.service;

import com.practice.project.chess.controller.dto.MoveDto;
import com.practice.project.chess.controller.dto.mapper.MoveDtoMapper;
import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.PlayerMove;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.MoveRepository;
import com.practice.project.chess.repository.PlayerMoveRepository;
import com.practice.project.chess.service.player.PlayerService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MoveService {

    private final MoveRepository moveRepository;
    private final PlayerMoveRepository playerMoveRepository;

    private final PlayerService playerService;
    private final BoardService boardService;

    private final MoveDtoMapper moveDtoMapper;

    public Move getMove(long id) {
        return moveRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Move not found!"));
    }

    public MoveDto getMoveDto(long id) {
        return moveRepository.findById(id)
                .map(moveDtoMapper::MoveToMoveDto)
                .orElseThrow(() -> new ElementNotFoundException("Move not found!"));
    }

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
        Move move = Move.builder() // TODO : id?
                .piece(piece)
                .horizontalFrom(piece.getHorizontalPosition())
                .verticalFrom(piece.getVerticalPosition())
                .horizontalTo(destination.getXPos())
                .verticalTo(destination.getYPos())
                .takenPiece(takenPiece)
                .build();
        return moveRepository.save(move);
    }

    public void updateSpecialMove(Move move, CastleType castleType, PieceType promotedTo) {
        if (castleType != null)
            move.setCastleType(castleType);
        if (promotedTo != null)
            move.setPromotedTo(promotedTo);
    }

    public void setTakenPieceIfEnPassant(Move move, long gameId) {
        if (pawnMovedDiagonally(move) && move.getTakenPiece() == null) {
            Piece takenPiece = boardService.getBoardMapForGame(gameId)
                    .getPieceByPos(move.getHorizontalTo(), move.getVerticalFrom());
            move.setTakenPiece(takenPiece);
        }

    }

    private boolean pawnMovedDiagonally(Move move) {
        return (move.getPiece().getPieceType() == PieceType.PAWN &&
                move.getHorizontalFrom() != move.getHorizontalTo());
    }
}
