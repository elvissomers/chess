package com.practice.project.chess.service.player;

import com.practice.project.chess.controller.dto.PlayerDto;
import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.controller.dto.mapper.PlayerDtoMapper;
import com.practice.project.chess.repository.entity.Move;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.PlayerMove;
import com.practice.project.chess.repository.entity.pieces.King;
import com.practice.project.chess.repository.entity.pieces.Pawn;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.MoveService;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.piece.PawnService;
import com.practice.project.chess.service.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class PlayerService {

    private PieceService pieceService;
    private PawnService pawnService;
    private MoveService moveService;

    private PlayerRepository playerRepository;

    private PlayerDtoMapper playerDtoMapper;

    public Player getPlayer(long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
    }

    public PlayerDto getPlayerDto(long id) {
        return playerRepository.findById(id)
                .map(playerDtoMapper::playerToPlayerDto)
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
    }

    public void setStartPiecesForPlayer(Player player) {
        PieceType[] pieceTypesInOrder = new PieceType[]{
                PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING,
                PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
        };
        int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
        int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

        for (int xPos = 0; xPos < BoardSize.horizontalSize; xPos++) {
            player.getPieces().add(pieceService.createPiece(pieceTypesInOrder[xPos], player,
                    xPos, yForMajorPieces)
            );
        }

        for (int xPos = 0; xPos < BoardSize.horizontalSize; xPos++) {
            player.getPieces().add(pieceService.createPiece(PieceType.PAWN, player,
                    xPos, yForPawns)
            );
        }
        playerRepository.save(player);
    }

    public List<Coordinate> getAllAttackedSquaresForPlayer(Player player) {
        List<Coordinate> attackedSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN)
                attackedSquares.addAll(pieceService.getAttackedSquaresForPawn(piece.getId()).getSquares());
            else
                attackedSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()).getSquares());
        }
        return attackedSquares;
    }

    public List<Coordinate> getAllMovableSquaresForPlayer(Player player) {
        List<Coordinate> movableSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            movableSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()).getSquares());
        }
        return movableSquares;
    }

    public void setAllAttackedAndMovableSquaresForPlayer(Player player) {
        long gameId = player.getGame().getId();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN) {
                pawnService.setAttackedSquares((Pawn) piece, gameId);
            }
            pieceService.setMovableSquaresForPiece(piece, gameId);
        }
    }
    //TODO: variant of above method but with board, to simplify legal move pruning

    public King getPlayerKing(Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece instanceof King)
                return (King) piece;
        }
        throw new ElementNotFoundException("Player's King not found!");
    }

    public Coordinate getPlayerKingCoordinate(Player player) {
        King playerKing = getPlayerKing(player);
        return new Coordinate(playerKing.getHorizontalPosition(), playerKing.getVerticalPosition());
    }

    public int getNumberOfMoves(long playerId) {
        return getPlayerMovesInOrder(playerId).size();
    }

    public List<PlayerMove> getPlayerMovesInOrder(long playerId) {
        return moveService.getPlayerMoves(playerId).stream()
                .sorted(Comparator.comparingInt(PlayerMove::getNumber))
                .toList();
    }

    public Move getLastMove(long playerId) {
        return moveService.getPlayerMoves(playerId).stream()
                .max(Comparator.comparingInt(PlayerMove::getNumber))
                .map(PlayerMove::getMove)
                .orElse(null);
    }

    public List<Move> getLastNMoves(int n, long playerId) {
        return moveService.getPlayerMoves(playerId).stream()
                .sorted(Comparator.comparingInt(PlayerMove::getNumber).reversed())
                .map(PlayerMove::getMove)
                .limit(n)
                .toList();
    }
}
