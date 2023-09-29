package com.practice.project.chess.service;

import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LegalMoveService {

    private final GameService gameService;

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
}
