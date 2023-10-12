package com.practice.project.chess.service.logic.game;

import com.practice.project.chess.repository.dao.MoveDao;
import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.CastleType;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.logic.MoveService;
import com.practice.project.chess.service.logic.player.PlayerService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.practice.project.chess.service.logic.AllUtil.getOpponentPlayer;
import static com.practice.project.chess.service.logic.game.util.MoveUtil.updateSpecialMove;
import static com.practice.project.chess.service.logic.game.GameService.getPieceForTeamAndPosition;
import static com.practice.project.chess.service.logic.game.util.MakeMoveUtil.*;

@AllArgsConstructor
@Service
public class MakeMoveService {

    private final PlayerService playerService;
    private final PieceService pieceService;
    private final MoveService moveService;

    private final GameStateService gameStateService;

    public void makeMove(Game game, Piece piece, Coordinate destination) {
        checkMovePossibility(game, piece, destination);
        Move madeMove = updateMoveHistory(game, piece, destination); // TODO: this could just be move, which is used next
        updatePlayerPiecesAfterMove(playerInTurn(game), madeMove);
        gameStateService.updateGameState(game);
    }

    private void checkMovePossibility(Game game, Piece piece, Coordinate destination) {
        pieceService.checkMoveLegality(piece, destination);
        checkIfPieceInTurn(game, piece);
    }

    private Move updateMoveHistory(Game game, Piece piece, Coordinate destination) {
        // TODO: could be shorter
        Team opponentTeam = getOtherTeam(piece.getTeam());
        Piece takenPiece = getPieceForTeamAndPosition(game, opponentTeam, destination);
        PieceType promotionPiece = getNewPieceIfPromoted(piece, destination);

        PieceDao movedPieceDao = pieceService.getOriginalDaoOfPiece(piece);
        PieceDao takenPieceDao = pieceService.getOriginalDaoOfPiece(takenPiece);
        MoveDao newMove = moveService.getOrCreateMove(movedPieceDao, destination, takenPieceDao, promotionPiece);

        CastleType castleType = getTypeIfCastled(newMove);
        updateSpecialMove(newMove, castleType, promotionPiece);
        moveService.setTakenPieceIfEnPassant(newMove, game.getId());
        return playerService.saveMoveForPlayer(newMove, playerInTurn(game));
    }

    private void updatePlayerPiecesAfterMove(Player player, Move move) {
        if (move.getTakenPiece() != null)
            playerService.removePiece(player, move.getTakenPiece());
        else if (move.getPromotedTo() != null)
            playerService.promotePawnTo(player, move);
        else
            playerService.updateDaoListFromMovedPiece(player, pieceService.getPieceWithNewPosition(move));
    }
}
