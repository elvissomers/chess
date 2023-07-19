package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.data.dto.PieceDto;
import com.ordina.nl.chess.data.dto.SquaresDto;
import com.ordina.nl.chess.data.dto.mapper.PieceDtoMapper;
import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.enums.MovementType;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.enums.Team;
import com.ordina.nl.chess.exception.ElementNotFoundException;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.service.BoardService;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class PieceService {

    private final BoardService boardService;

    private final PawnService pawnService;
    private final KnightService knightService;
    private final BishopService bishopService;
    private final RookService rookService;
    private final QueenService queenService;
    private final KingService kingService;

    private final PieceRepository pieceRepository;

    private final PieceDtoMapper pieceDtoMapper;

    public Piece getPiece(long id) {
        return pieceRepository.findById(id)
                .orElse(null);
        // TODO: .orElseThrow(ElementNotFoundException)
    }

    public PieceDto getPieceDto(long id) {
        return pieceRepository.findById(id)
                .map(pieceDtoMapper::pieceToPieceDto)
                .orElse(null);
        // TODO: .orElseThrow(ElementNotFoundException)
    }

    public SquaresDto getMovableSquaresForPiece(PieceDto pieceDto) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(pieceDto.getId())
                .map(Piece::getMovableSquares)
                .orElse(null)) // TODO: .orElseThrow
                .build();
    }

    public SquaresDto getAttackedSquaresForPawn(PieceDto pieceDto) {
        return pawnService.getAttackedSquares(pieceDto);
    }

    public Piece createPiece(PieceType pieceType, Player player, int horizontalPosition, int verticalPosition) {
        Piece piece = null;
        switch(pieceType) {
            case KING -> piece = new King();
            case PAWN -> piece = new Pawn();
            case ROOK -> piece = new Rook();
            case QUEEN -> piece = new Queen();
            case BISHOP -> piece = new Bishop();
            case KNIGHT -> piece = new Knight();
        }

        piece.setHorizontalPosition(horizontalPosition);
        piece.setVerticalPosition(verticalPosition);
        piece.setPlayer(player);

        return pieceRepository.save(piece);
    }

    public void setMovementTypesForPiece(Piece piece) {
        List<MovementType> movementTypes = piece.getMovementTypes();
        switch(piece.getPieceType()) {
            case KNIGHT -> movementTypes.add(MovementType.LSHAPED);
            case BISHOP -> movementTypes.add(MovementType.DIAGONAL);
            case KING -> movementTypes.add(MovementType.KING);
            case PAWN -> movementTypes.add(MovementType.PAWN);
            case ROOK -> {
                movementTypes.add(MovementType.HORIZONTAL);
                movementTypes.add(MovementType.VERTICAL);
            }
            case QUEEN -> {
                movementTypes.add(MovementType.DIAGONAL);
                movementTypes.add(MovementType.HORIZONTAL);
                movementTypes.add(MovementType.VERTICAL);
            }
        }
    }

    public void pruneSelfCheckMovesForPieceInGame(Piece piece, GameDto game) {
        for (Coordinate moveOption : piece.getMovableSquares()){
            Piece copyPiece = piece.copy();
            copyPiece.setHorizontalPosition(moveOption.getXPos());
            copyPiece.setVerticalPosition(moveOption.getYPos());

            BoardMap copyBoard = boardService.setBoardMapForCopiedPiece(piece, copyPiece, game);
            setAllAttackedSquaresForEnemyPlayer(piece.getPlayer().getTeam(), copyBoard, game);
            try {
                Coordinate kingCoordinate = new Coordinate(piece.getPlayer().getKing().getHorizontalPosition(),
                        piece.getPlayer().getKing().getVerticalPosition());
                if (checkCheck(kingCoordinate, copyBoard, piece.getPlayer().getTeam(), game)) {
                    continue;
                }
            } catch (ElementNotFoundException exception) {
                exception.printStackTrace();
            }

            piece.getLegalMovableSquares().add(moveOption);
        }
    }


}
