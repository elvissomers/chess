package com.practice.project.chess.service.gameservice;

import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.Player;
import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.gameservice.GameService;
import com.practice.project.chess.service.gameservice.pieces.PieceService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LegalMoveService {

    // "Level" of GameService (top level)

    private final PieceService pieceService;
    private final PlayerService playerService;
    private final BoardService boardService;
    private final EnPassantService enPassantService;

    private Player player;
    private Player opponentPlayer;

    public void setLegalMovableSquaresForPiece(Piece piece, Game game) {
        pieceService.setMovableSquaresForPiece(piece, game.getId());
        setPlayers(piece, game);
        if (piece.getPieceType() == PieceType.PAWN) {
            enPassantService.addPawnEnPassantMovesToMovableSquares(piece, game);
        }

        for (Coordinate moveOption : piece.getMovableSquares()) {
            Piece copyPiece = copyPieceTo(piece, moveOption);

            BoardMap copyBoard = boardService.getBoardMapForCopiedPiece(piece, copyPiece, game.getId());
            Coordinate kingPosition = playerService.getPlayerKingCoordinate(player);

            List<Coordinate> attackedSquares = getAllAttackedSquaresForPlayer(opponentPlayer, copyBoard);
            if (!attackedSquares.contains(kingPosition)) {
                piece.getLegalMovableSquares().add(moveOption);
            }
        }
    }

    private void setPlayers(Piece piece, Game game) {
        // TODO: should we remove the "player" attribute of piece? And the "game" attribute of player?
        // TODO: this would achieve better SOC
        if (piece.getPlayer().getTeam() == Team.WHITE) {
            player = game.getWhitePlayer();
            opponentPlayer = game.getBlackPlayer();
        } else {
            player = game.getBlackPlayer();
            opponentPlayer = game.getWhitePlayer();
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
