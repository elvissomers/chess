package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.MoveDto;
import com.practice.project.chess.data.dto.mapper.MoveDtoMapper;
import com.practice.project.chess.entity.Move;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.CastleType;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.repository.MoveRepository;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MoveService {

    private final MoveRepository moveRepository;

    private final MoveDtoMapper moveDtoMapper;

    public Move getMove(long id) throws ElementNotFoundException {
        return moveRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Move not found!"));
    }

    public MoveDto getMoveDto(long id) throws ElementNotFoundException {
        return moveRepository.findById(id)
                .map(moveDtoMapper::MoveToMoveDto)
                .orElseThrow(() -> new ElementNotFoundException("Move not found!"));
    }

    public List<MoveDto> getMovesFromPlayerId(long id) {
        // TODO: so how do we make sure the moves are in order?
        // TODO PlayerMove "koppelobject"?
        return moveRepository.findByPlayers_IdContaining(id).stream()
                .map(moveDtoMapper::MoveToMoveDto)
                .toList();
    }

    public Move createMove(Piece piece, Coordinate destination, boolean takenPiece) {
        return Move.builder() // TODO : id?
                .piece(piece)
                .horizontalFrom(piece.getHorizontalPosition())
                .verticalFrom(piece.getVerticalPosition())
                .horizontalTo(destination.getXPos())
                .verticalTo(destination.getYPos())
                .takenPiece(takenPiece)
                .build();
    }

    public void updateSpecialMove(Move move, CastleType castleType, PieceType promotedTo) {
        if (castleType != null)
            move.setCastleType(castleType);
        if (promotedTo != null)
            move.setPromotedTo(promotedTo);
    }
}
