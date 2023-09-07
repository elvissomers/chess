package com.practice.project.chess.service.pieces;

import com.practice.project.chess.data.dto.PieceDto;
import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.data.dto.mapper.PieceDtoMapper;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.exception.InvalidMoveException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.GameService;
import com.practice.project.chess.service.PlayerService;
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
    private final PlayerService playerService;

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

    public Piece getPieceForGameAndPosition(int xPos, int yPos, long gameId) throws ElementNotFoundException {
        return pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xPos, yPos, gameId)
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

    // TODO: can this method be private?
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
        setMovableSquaresForPiece(piece, gameId);

        for (Coordinate moveOption : piece.getMovableSquares()) {
            Piece copyPiece = copyPieceTo(piece, moveOption);

            BoardMap copyBoard = boardService.getBoardMapForCopiedPiece(piece, copyPiece, gameId);
            Player opponentPlayer = gameService.getOpponentPlayerForGameAndTeam(gameId, piece.getPlayer().getTeam());

            Player player = piece.getPlayer();
            Coordinate kingPosition = playerService.getPlayerKingCoordinate(player);

            List<Coordinate> attackedSquares = getAllAttackedSquaresForPlayer(opponentPlayer, copyBoard);
            if (!attackedSquares.contains(kingPosition)) {
                piece.getLegalMovableSquares().add(moveOption);
            }
        }
    }

    private Piece copyPieceTo(Piece piece, Coordinate destination) {
        Piece copyPiece = piece.copy();
        copyPiece.setHorizontalPosition(destination.getXPos());
        copyPiece.setVerticalPosition(destination.getYPos());

        return copyPiece;
    }

    private List<Coordinate> getAllAttackedSquaresForPlayer(Player player, BoardMap board) {
        List<Coordinate> attackedSquares = new ArrayList<>();
        for (Piece enemyPiece : player.getPieces()){
            Piece enemyCopyPiece = enemyPiece.copy();
            setAttackedSquaresForPieceWithBoard(enemyCopyPiece, board);
            attackedSquares.addAll(getAttackedSquaresForPiece(enemyCopyPiece));
        }

        return attackedSquares;
    }

    public void checkMoveLegality(long pieceId, Coordinate destination)
            throws InvalidMoveException, ElementNotFoundException {
        Piece piece = getPiece(pieceId);
        if (piece.getLegalMovableSquares().contains(destination))
            return;
        throw new InvalidMoveException("Illegal Move!");
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
}
