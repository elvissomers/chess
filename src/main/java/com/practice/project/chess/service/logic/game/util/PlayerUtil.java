package com.practice.project.chess.service.logic.game.util;

import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.King;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.structures.Coordinate;

public final class PlayerUtil {

    private PlayerUtil() {
    }


    public static Piece getPlayerPieceOnCoordinate(Player player, Coordinate coordinate) {
        // TODO: player should be mapped to entity as well, that holds a list to pieces
        // TODO instead of pieceDao's!
        for (Piece piece : player.getPieces()) {
            if (piece.getCoordinate().equals(coordinate))
                return piece;
        }
        throw new ElementNotFoundException("No piece found at this position!");
    }

    public static King getPlayerKing(Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece instanceof King)
                return (King) piece;
        }
        throw new ElementNotFoundException("Player's King not found!");
    }

    public static Coordinate getPlayerKingCoordinate(Player player) {
        King playerKing = getPlayerKing(player);
        return new Coordinate(playerKing.getHorizontalPosition(), playerKing.getVerticalPosition());
    }
}
