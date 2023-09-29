package com.practice.project.chess.service.gameservice.pieces;

import com.practice.project.chess.controller.dto.PieceDto;
import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.controller.dto.mapper.PieceDtoMapper;
import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.exception.InvalidMoveException;
import com.practice.project.chess.repository.PieceRepository;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.gameservice.GameService;
import com.practice.project.chess.service.gameservice.PlayerService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.repository.entity.pieces.*;
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
    private final PlayerRepository playerRepository;

    private final PieceDtoMapper pieceDtoMapper;

    public Piece getPiece(long id) {
        return pieceRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found!"));
    }

    public PieceDto getPieceDto(long id) {
        return pieceRepository.findById(id)
                .map(pieceDtoMapper::pieceToPieceDto)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found!"));
    }

    public Piece getPieceForGameAndPosition(int xPos, int yPos, long gameId) {
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

    public void setLegalMovableSquaresForPiece(Piece piece, long gameId) {
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
