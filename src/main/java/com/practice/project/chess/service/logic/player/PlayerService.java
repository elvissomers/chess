package com.practice.project.chess.service.logic.player;

import com.practice.project.chess.repository.PlayerMoveRepository;
import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.mapper.PlayerMapper;
import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.Player;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import com.practice.project.chess.service.model.pieces.Pawn;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.PieceType;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.PlayerRepository;
import com.practice.project.chess.service.logic.piece.PawnService;
import com.practice.project.chess.service.logic.piece.PieceService;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PlayerService {

    private final PieceService pieceService;
    private final PawnService pawnService;

    private final PlayerRepository playerRepository;
    private final PlayerMoveRepository playerMoveRepository;

    private final PlayerMapper playerMapper;

    public PlayerDao getDaoForPlayer(Player player) {
        return playerRepository.findById(player.getId())
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
    }

    public Player getPlayer(long id) {
        return playerRepository.findById(id)
                .map(playerMapper::daoToPlayer)
                .orElseThrow(() -> new ElementNotFoundException("Player not found!"));
    }

    public int getNumberOfMoves(long playerId) {
        return getPlayerMovesInOrder(playerId).size();
    }

    public List<Move> getPlayerMovesInOrder(long playerId) {
        return getPlayer(playerId).getMoveHistory();
    }

    public Move getLastMove(long playerId) {
        List<Move> moves = getPlayerMovesInOrder(playerId);
        return moves.isEmpty() ? null : moves.get(moves.size() - 1);
    }

    public List<Move> getLastNMoves(int n, long playerId) {
        List<Move> moves = getPlayerMovesInOrder(playerId);
        if (moves.size() < n) {
            return getPlayerMovesInOrder(playerId);
        }
        return moves.subList(moves.size() - n, moves.size());
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
    }

    // TODO: vraag voor Nick: is het okee om de Game door te geven aan een method in player service;
    // TODO ook al heeft de player zelf supposedly geen idee van in welke game hij is?
    // TODO of is dit impropere SOC?
    public void setAllAttackedAndMovableSquaresForPlayer(Game game, Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece.getPieceType() == PieceType.PAWN) {
                pawnService.setAttackedSquares((Pawn) piece, game.getId());
            }
            pieceService.setMovableSquaresForPiece(piece, game.getId());
        }
    }

    public PlayerMove saveMoveForPlayer(Move move, Player player) {
        PlayerMove newMove = PlayerMove.builder()
                .number(getNumberOfMoves(player.getId()) + 1)
                .move(move)
                .player(player)
                .build();
        return playerMoveRepository.save(newMove);
    }

    public void promotePawnTo(Player player, Move move) {
        PlayerDao playerDao = getDaoForPlayer(player);
        Piece pawn = move.getPiece();
        playerDao.getPieces().remove(pieceService.getOriginalDaoOfPiece(pawn));

        Piece promotionPiece = pieceService.createPiece(move.getPromotedTo(), player, move.getHorizontalTo(), move.getVerticalTo());
        playerDao.getPieces().add(pieceService.getNewDaoOfPiece(promotionPiece));
        playerRepository.save(playerDao);
    }

    public void removePiece(Player player, Piece piece) {
        PlayerDao playerDao = getDaoForPlayer(player);
        playerDao.getPieces().remove(pieceService.getOriginalDaoOfPiece(piece));
    }

    public void updateDaoListFromMovedPiece(Player player, Piece piece) {
        PlayerDao playerDao = getDaoForPlayer(player);
        playerDao.getPieces().remove(pieceService.getOriginalDaoOfPiece(piece));
        playerDao.getPieces().add(pieceService.getNewDaoOfPiece(piece));
        playerRepository.save(playerDao);
    }
}
