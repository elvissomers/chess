package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.PlayerDto;
import com.practice.project.chess.data.dto.SquaresDto;
import com.practice.project.chess.data.dto.mapper.PlayerDtoMapper;
import com.practice.project.chess.entity.Move;
import com.practice.project.chess.entity.Player;
import com.practice.project.chess.entity.PlayerMove;
import com.practice.project.chess.entity.pieces.King;
import com.practice.project.chess.entity.pieces.Pawn;
import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.enums.PieceType;
import com.practice.project.chess.enums.Team;
import com.practice.project.chess.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.pieces.PawnService;
import com.practice.project.chess.service.pieces.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.constants.BoardSize;
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

    public Player getPlayer(long id) throws ElementNotFoundException {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
    }

    public PlayerDto getPlayerDto(long id) throws ElementNotFoundException {
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

    // TODO: why use squaresDto? Instead of just a list; List<Coordinate>?
    public SquaresDto getAllAttackedSquaresForPlayer(Player player) throws ElementNotFoundException {
        List<Coordinate> attackedSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN)
                attackedSquares.addAll(pieceService.getAttackedSquaresForPawn(piece.getId()).getSquares());
            else
                attackedSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()).getSquares());
        }
        return SquaresDto.builder().squares(attackedSquares).build();
    }

    public List<Coordinate> getAllMovableSquaresForPlayer(Player player) throws ElementNotFoundException {
        List<Coordinate> movableSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            movableSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()).getSquares());
        }
        return movableSquares;
    }

    public void setAllAttackedAndMovableSquaresForPlayer(Player player) throws ElementNotFoundException {
        long gameId = player.getGame().getId();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN) {
                pawnService.setAttackedSquares((Pawn) piece, gameId);
            }
            pieceService.setMovableSquaresForPiece(piece, gameId);
        }
    }
    //TODO: variant of above method but with board, to simplify legal move pruning

    public King getPlayerKing(Player player) throws ElementNotFoundException {
        for (Piece piece : player.getPieces()) {
            if (piece instanceof King)
                return (King) piece;
        }
        throw new ElementNotFoundException("Player's King not found!");
    }

    public Coordinate getPlayerKingCoordinate(Player player) throws ElementNotFoundException {
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
