package com.practice.project.chess.service.pieces;

import com.practice.project.chess.data.dto.PieceDto;
import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.data.dto.mapper.PieceDtoMapper;
import com.practice.project.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.entity.pieces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public SquaresDto getMovableSquaresForPiece(long id) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(id)
                        .map(Piece::getMovableSquares)
                        .orElse(null)) // TODO: .orElseThrow
                .build();
    }

    public SquaresDto getAttackedSquaresForPawn(long pieceId) {
        return pawnService.getAttackedSquares(pieceId);
    }

    public void setMovableSquaresForPiece(Piece piece, long gameId) {
        switch(piece.getPieceType()) {
            case PAWN -> pawnService.setMovableSquares(piece, gameId);
            case KNIGHT -> knightService.setMovableSquares(piece, gameId);
            case BISHOP -> bishopService.setMovableSquares(piece, gameId);
            case ROOK -> rookService.setMovableSquares(piece, gameId);
            case QUEEN -> queenService.setMovableSquares(piece, gameId);
            case KING -> kingService.setMovableSquares(piece, gameId);
        }
    };

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

    public void getLegalMovesForPiece(Piece piece, long gameId) {
        for (Coordinate moveOption : piece.getMovableSquares()){
            Piece copyPiece = piece.copy();
            copyPiece.setHorizontalPosition(moveOption.getXPos());
            copyPiece.setVerticalPosition(moveOption.getYPos());

            BoardMap copyBoard = boardService.getBoardMapForCopiedPiece(piece, copyPiece, gameId);
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
