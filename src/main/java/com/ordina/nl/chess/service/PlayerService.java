package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.data.dto.MoveDto;
import com.ordina.nl.chess.data.dto.PlayerDto;
import com.ordina.nl.chess.data.dto.mapper.PlayerDtoMapper;
import com.ordina.nl.chess.entity.Move;
import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.enums.Team;
import com.ordina.nl.chess.repository.PlayerRepository;
import com.ordina.nl.chess.service.pieces.PieceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
