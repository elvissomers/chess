package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.controller.dto.mapper.PieceDtoMapper;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.repository.dao.Player;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.exception.InvalidMoveException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.model.pieces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PieceService {

    private final PawnService pawnService;
    private final KnightService knightService;
    private final BishopService bishopService;
    private final RookService rookService;
    private final QueenService queenService;
    private final KingService kingService;

    private final PieceRepository pieceRepository;
    private final PlayerRepository playerRepository;

    public Piece getPieceForGameAndPosition(int xPos, int yPos, long gameId) {
        // TODO: refactor to get Piece for coordinate in PlayerService
        return pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xPos, yPos, gameId)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found!"));
    }

    public SquaresDto getMovableSquaresForPiece(long id) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(id)
                        .map(Piece::getMovableSquares)
                        .orElseThrow(() -> new ElementNotFoundException("Piece not found!"))
                ).build();
    }

    public SquaresDto getAttackedSquaresForPawn(long pieceId) {
        return pawnService.getAttackedSquares(pieceId);
    }

    public List<Coordinate> getAttackedSquaresForPiece(Piece piece) {
        return (piece.getPieceType() == PieceType.PAWN) ? piece.getAttackedSquares()
                : piece.getMovableSquares();
        // TODO: make attacked squares be an attribute of pawn specifically?
    }

    // TODO: can this method be private?
    public void setMovableSquaresForPiece(Piece piece, long gameId) {
        switch(piece.getPieceType()) {
            case PAWN -> pawnService.setMovableSquares(piece, gameId);
            case KNIGHT -> knightService.setMovableSquares(piece, gameId);
            case BISHOP -> bishopService.setMovableSquares(piece, gameId);
            case ROOK -> rookService.setMovableSquares(piece, gameId);
            case QUEEN -> queenService.setMovableSquares(piece, gameId);
            case KING -> kingService.setMovableSquares(piece, gameId);
        }
    }

    public void setAttackedSquaresForPieceWithBoard(Piece piece, BoardMap board) {
        switch(piece.getPieceType()) {
            case PAWN -> pawnService.setAttackedSquaresWithBoard(piece, board);
            case KNIGHT -> knightService.setMovableSquaresWithBoard(piece, board);
            case BISHOP -> bishopService.setMovableSquaresWithBoard(piece, board);
            case ROOK -> rookService.setMovableSquaresWithBoard(piece, board);
            case QUEEN -> queenService.setMovableSquaresWithBoard(piece, board);
            case KING -> kingService.setMovableSquaresWithBoard(piece, board);
        }
    }

    public void checkMoveLegality(Piece piece, Coordinate destination) {
        if (piece.getLegalMovableSquares().contains(destination))
            return;
        throw new InvalidMoveException("Illegal Move!");
    }

    public void updatePosition(Move move) {
        Piece piece = move.getPiece();
        piece.setHorizontalPosition(move.getHorizontalTo());
        piece.setVerticalPosition(move.getVerticalTo());
        pieceRepository.save(piece);
    }

    public void promotePawnTo(Move move) {
        Piece pawn = move.getPiece();
        Player player = pawn.getPlayer();
        player.getPieces().remove(pawn);
        Piece promotionPiece = createPiece(move.getPromotedTo(), player, move.getHorizontalTo(), move.getVerticalTo());
        player.getPieces().add(promotionPiece);
        playerRepository.save(player);
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

    public void removePiece(Piece piece) {
        Player player = piece.getPlayer();
        player.getPieces().remove(piece);

        pieceRepository.delete(piece);
        playerRepository.save(player);
    }
}
