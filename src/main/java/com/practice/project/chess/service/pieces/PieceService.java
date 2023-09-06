package com.practice.project.chess.service.pieces;

import com.practice.project.chess.data.dto.PieceDto;
import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.data.dto.mapper.PieceDtoMapper;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.GameService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.entity.pieces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PieceService {

    private final BoardService boardService;
    private final GameService gameService;

    private final PawnService pawnService;
    private final KnightService knightService;
    private final BishopService bishopService;
    private final RookService rookService;
    private final QueenService queenService;
    private final KingService kingService;

    private final PieceRepository pieceRepository;

    private final PieceDtoMapper pieceDtoMapper;

    public Piece getPiece(long id) throws ElementNotFoundException {
        return pieceRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found!"));
    }

    public PieceDto getPieceDto(long id) throws ElementNotFoundException {
        return pieceRepository.findById(id)
                .map(pieceDtoMapper::pieceToPieceDto)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found!"));
    }

    public SquaresDto getMovableSquaresForPiece(long id) throws ElementNotFoundException {
        return SquaresDto.builder().squares(
                pieceRepository.findById(id)
                        .map(Piece::getMovableSquares)
                        .orElseThrow(() -> new ElementNotFoundException("Piece not found!"))
                ).build();
    }

    public SquaresDto getAttackedSquaresForPawn(long pieceId) throws ElementNotFoundException {
        return pawnService.getAttackedSquares(pieceId);
    }

    public SquaresDto getAttackedSquaresForPiece(long pieceId) throws ElementNotFoundException {
        return (getPiece(pieceId).getPieceType() == PieceType.PAWN) ? getAttackedSquaresForPawn(pieceId)
                : getMovableSquaresForPiece(pieceId);
    }

    private List<Coordinate> getAttackedSquaresForPiece(Piece piece) {
        return (piece.getPieceType() == PieceType.PAWN) ? piece.getAttackedSquares()
                : piece.getMovableSquares();
        // TODO: make attacked squares be an attribute of pawn specifically?
    }

    public void setMovableSquaresForPiece(Piece piece, long gameId) throws ElementNotFoundException {
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

    public void setLegalMovableSquaresForPiece(Piece piece, long gameId) throws ElementNotFoundException {
        for (Coordinate moveOption : piece.getMovableSquares()) {
            Piece copyPiece = piece.copy();
            copyPiece.setHorizontalPosition(moveOption.getXPos());
            copyPiece.setVerticalPosition(moveOption.getYPos());

            BoardMap copyBoard = boardService.getBoardMapForCopiedPiece(piece, copyPiece, gameId);
            Player opponentPlayer = gameService.getPlayerForGameAndTeam(gameId, piece.getPlayer().getTeam());

            List<Coordinate> attackedSquares = new ArrayList<>();
            for (Piece enemyPiece : opponentPlayer.getPieces()){
                Piece enemyCopyPiece = enemyPiece.copy();
                setAttackedSquaresForPieceWithBoard(enemyCopyPiece, copyBoard);
                attackedSquares.addAll(getAttackedSquaresForPiece(enemyCopyPiece));
            }
            if (!attackedSquares.contains(moveOption)) {
                piece.getLegalMovableSquares().add(moveOption);
            }
        }
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
