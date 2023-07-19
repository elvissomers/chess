package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.*;
import com.ordina.nl.chess.data.dto.mapper.PlayerDtoMapper;
import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.entity.Move;
import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.enums.Team;
import com.ordina.nl.chess.repository.PlayerRepository;
import com.ordina.nl.chess.service.pieces.PieceService;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import static com.ordina.nl.chess.constants.BoardSize.horizontalSize;

@AllArgsConstructor
@Service
public class PlayerService {

    private PieceService pieceService;
    private MoveService moveService;

    private PlayerRepository playerRepository;

    private PlayerDtoMapper playerDtoMapper;

    public Player getPlayer(long id) {
        return playerRepository.findById(id)
                .orElse(null);
        // TODO; when BaseExceptionHandler is put up:
//                .orElseThrow(() -> new ElementNotFoundException("!"));
    }

    public PlayerDto getPlayerDto(long id) {
        return playerRepository.findById(id)
                .map(playerDtoMapper::playerToPlayerDto)
                .orElse(null);
        // TODO; when BaseExceptionHandler is put up:
//                .orElseThrow(() -> new ElementNotFoundException("!"));
    }

    public void setStartPiecesForPlayer(Player player) {
        PieceType[] pieceTypesInOrder = new PieceType[]{
                PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING,
                PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
        };
        int yForMajorPieces = (player.getTeam() == Team.WHITE) ? 0 : 7;
        int yForPawns = (player.getTeam() == Team.WHITE) ? 1 : 6;

        for (int xPos = 0; xPos < horizontalSize; xPos++) {
            player.getPieces().add(pieceService.createPiece(pieceTypesInOrder[xPos], player,
                    xPos, yForMajorPieces)
            );
        }

        for (int xPos = 0; xPos < horizontalSize; xPos++) {
            player.getPieces().add(pieceService.createPiece(PieceType.PAWN, player,
                    xPos, yForPawns)
            );
        }
    }

    public SquaresDto getAllAttackedSquaresForPlayer(PlayerDto player) {
        List<Coordinate> attackedSquares = new ArrayList<>();
        for (PieceDto piece : player.getPlayerPiecesDto().getPieces()) {
            (piece.getPieceType() == PieceType.PAWN) ? attackedSquares.addAll(pieceService.getMovableSquaresForPiece(piece).getSquares())
                : attackedSquares.addAll(pieceService.getAttackedSquaresForPawn(piece));
        }
        return SquaresDto.builder().squares(attackedSquares).build();
    }

    public void setAllAttackedAndMovableSquaresForPlayer(Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN) {
                pieceService.setAttackedSquaresForPawn(piece);
            }
            pieceService.setMovableSquaresForPiece(piece);
        }
    }

    public void setAllAttackedSquares(BoardMap board) {
        allAttackedSquares = new HashSet<>();
        for (Piece piece : this.pieces){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares(board);
                allAttackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares(board);
                allAttackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }

    public void setAllAttackedSquaresForEnemyPlayer(Team team, BoardMap board, Game game) {
        Player enemyPlayer = (team == Team.WHITE) ? game.getBlackPlayer() : game.getWhitePlayer();
        enemyPlayer.setAllAttackedSquares(board);
    }

    public List<MoveDto> getPlayerMovesInOrder(PlayerDto playerDto) {
        List<MoveDto> playerMoves = moveService.getMovesFromPlayerId(playerDto.getId());
        return playerMoves.stream()
                .sorted(Comparator.comparingInt(MoveDto::getNumber))
                .toList();
    }

    public MoveDto getLastMove(PlayerDto playerDto) {
        List<MoveDto> playerMoves = moveService.getMovesFromPlayerId(playerDto.getId());
        return playerMoves.stream()
                .max(Comparator.comparingInt(MoveDto::getNumber))
                .orElse(null);
    }

    public List<MoveDto> getLastNMoves(int n, PlayerDto playerDto) {
        List<MoveDto> playerMoves = moveService.getMovesFromPlayerId(playerDto.getId());
        return playerMoves.stream()
                .sorted(Comparator.comparingInt(MoveDto::getNumber).reversed())
                .limit(n)
                .toList();
    }
}
