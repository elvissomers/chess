package com.ordina.nl.chess.service;

import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.enums.PieceType;
import com.ordina.nl.chess.enums.Team;
import com.ordina.nl.chess.service.pieces.PieceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ordina.nl.chess.constants.BoardSize.horizontalSize;

@AllArgsConstructor
@Service
public class PlayerService {

    private PieceService pieceService;

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
}
