package com.practice.project.chess.service.logic.player;

import com.practice.project.chess.repository.PlayerMoveRepository;
import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.logic.MoveService;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.logic.piece.PawnService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class PlayerService {

    private final PieceService pieceService;
    private final PawnService pawnService;
    private final MoveService moveService;

    private final PlayerRepository playerRepository;
    private final PlayerMoveRepository playerMoveRepository;

    public PlayerDao getDaoForPlayer(Player player) {
        return playerRepository.findById(player.getId())
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
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

    public List<Coordinate> getAllAttackedSquaresForPlayer(Player player) {
        List<Coordinate> attackedSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN)
                attackedSquares.addAll(pieceService.getAttackedSquaresForPawn(piece.getId()).getSquares());
            else
                attackedSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()));
        }
        return attackedSquares;
    }

    public List<Coordinate> getAllMovableSquaresForPlayer(Player player) {
        List<Coordinate> movableSquares = new ArrayList<>();
        for (Piece piece : player.getPieces()) {
            movableSquares.addAll(pieceService.getMovableSquaresForPiece(piece.getId()));
        }
        return movableSquares;
    }

    public void setStartPiecesForPlayer(Player player) {
        // TODO: this should setup a playerDAO as well. Perhaps we could just hardcode the ID's of the starting pieces
        // TODO into here? Or something akin to that?
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

    public void setAllAttackedAndMovableSquaresForPlayer(Player player) {
        // TODO: this happens at game level, so should not be the players concern!
        // TODO: idem move setting.. Or just the ID?
        long gameId = player.getGame().getId();
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN) {
                pawnService.setAttackedSquares((Pawn) piece, gameId);
            }
            pieceService.setMovableSquaresForPiece(piece, gameId);
        }
    }
    //TODO: variant of above method but with board, to simplify legal move pruning

    public PlayerMove saveMoveForPlayer(Move move, Player player) {
        PlayerMove newMove = PlayerMove.builder()
                .number(getNumberOfMoves(player.getId()) + 1)
                .move(move)
                .player(player)
                .build();
        return playerMoveRepository.save(newMove);
    }

    public void promotePawnTo(Move move) {
        // TODO: should work without piece referring to player - player should be input and obtained where it is used
        Piece pawn = move.getPiece();
        Player player = pawn.getPlayer();
        player.getPieces().remove(pawn);
        Piece promotionPiece = pieceService.createPiece(move.getPromotedTo(), player, move.getHorizontalTo(), move.getVerticalTo());
        player.getPieces().add(promotionPiece);
        playerRepository.save(player);
    }

    public void removePiece(Piece piece) {
        // TODO: this should get Player (or playerDAO? as input, not requiring piece to have a player attribute)
        Player player = piece.getPlayer();
        player.getPieces().remove(pieceService.getOriginalDaoOfPiece(piece));
    }

    // TODO: when do we call this exactly?
    public void updateDaoListFromMovedPiece(Player player, Piece piece) {
        PlayerDao playerDao = getDaoForPlayer(player);
        playerDao.getPieces().remove(pieceService.getOriginalDaoOfPiece(piece));
        playerDao.getPieces().add(pieceService.getNewDaoOfPiece(piece));
        playerRepository.save(playerDao);
    }
}
