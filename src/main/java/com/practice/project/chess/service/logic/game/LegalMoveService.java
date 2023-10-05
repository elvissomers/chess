package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.service.logic.game.util.PlayerUtil;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
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
    private final CastlingService castlingService;

    private Player player;
    private Player opponentPlayer;

    public void setAllLegalMovableSquaresForPlayer(Player player, Game game) {
        // TODO : combine this more efficiently with method above, so we don't set the player we already have
        // TODO at every turn (probably when refactoring player being in piece)
        for (Piece piece : player.getPieces())
            setLegalMovableSquaresForPiece(piece, game);
    }

    private void setLegalMovableSquaresForPiece(Piece piece, Game game) {
        // TODO: this method should be actually called! We can call it in the setup of the game
        // TODO and then also after every move is made
        setPlayers(piece, game);

        pieceService.setMovableSquaresForPiece(piece, game.getId());
        setSpecialMoves(piece, game);

        for (Coordinate moveOption : piece.getMovableSquares()) {
            addToLegalMovableSquaresIfLegal(piece, moveOption, game);
        }
    }

    private void addToLegalMovableSquaresIfLegal(Piece piece, Coordinate destination, Game game) {
        Piece copyPiece = copyPieceTo(piece, destination);

        BoardMap copyBoard = boardService.getBoardMapForCopiedPiece(piece, copyPiece, game.getId());
        Coordinate kingPosition = PlayerUtil.getPlayerKingCoordinate(player);

        List<Coordinate> attackedSquares = getAllAttackedSquaresForPlayer(opponentPlayer, copyBoard);
        if (!attackedSquares.contains(kingPosition)) {
            piece.getLegalMovableSquares().add(destination);
        }
    }

    private void setPlayers(Piece piece, Game game) {
        // TODO: should we remove the "player" attribute of piece? And the "game" attribute of player?
        // TODO: this would achieve better SOC
        if (piece.getTeam() == Team.WHITE) {
            player = game.getWhitePlayer();
            opponentPlayer = game.getBlackPlayer();
        } else {
            player = game.getBlackPlayer();
            opponentPlayer = game.getWhitePlayer();
        }
    }

    private void setSpecialMoves(Piece piece, Game game) {
        if (piece.getPieceType() == PieceType.PAWN) {
            enPassantService.addPawnEnPassantMovesToMovableSquares(piece, game);
        }
        if (piece.getPieceType() == PieceType.KING) {
            castlingService.setKingCastlingMoves(piece, game);
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
