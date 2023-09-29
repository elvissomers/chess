package com.practice.project.chess.service.gameservice;

import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.gameservice.GameService;
import com.practice.project.chess.service.gameservice.pieces.PieceService;
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
    private final PieceService pieceService;
    private final PlayerService playerService;
    private final BoardService boardService;

    public void setLegalMovableSquaresForPiece(Piece piece, long gameId) {
        pieceService.setMovableSquaresForPiece(piece, gameId);

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
            pieceService.setAttackedSquaresForPieceWithBoard(enemyCopyPiece, board);
            attackedSquares.addAll(pieceService.getAttackedSquaresForPiece(enemyCopyPiece));
        }

        return attackedSquares;
    }
}
