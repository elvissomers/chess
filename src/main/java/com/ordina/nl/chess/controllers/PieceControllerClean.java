package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.exception.ElementNotFoundException;
import com.ordina.nl.chess.exception.InvalidMoveException;
import com.ordina.nl.chess.game.Move;
import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.movement.MoveFinder;
import com.ordina.nl.chess.pieces.King;
import com.ordina.nl.chess.pieces.Pawn;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.pieces.Queen;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.repository.MoveRepository;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.repository.PlayerRepository;
import com.ordina.nl.chess.structures.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

public class PieceControllerClean {

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MoveFinder moveFinder;

    // This is a get mapping, it should not change anything
    public List<Coordinate> getMovableSquares(long gameId, int xPos, int yPos) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xPos, yPos, gameId);

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return null;
        BoardMap board = moveFinder.setBoardMap(optionalGame.get());
        optionalGame.get().setMovableSquaresForPiece(optionalPiece.get(), board);

        // TODO: to DTO
        return optionalPiece.get().getLegalMovableSquares();
    }

    public void makeMove(long gameId, int xFrom, int yFrom, int xTo, int yTo)
            throws ElementNotFoundException, InvalidMoveException {
        Game game = findGameById(gameId);
        Piece piece = findPieceAtPositionForGame(xFrom, yFrom, gameId);
        Piece targetPiece = findPieceAtPositionForGame(xTo, yTo, gameId);

        validateMove(game, piece, xTo, yTo);

        if (piece instanceof King king) {
            handleKingMove(king, xTo, yTo, game, targetPiece);
            return;
        }

        if (piece instanceof Pawn pawn) {
            handlePawnMove(pawn, xTo, yTo, targetPiece);
            return;
        }

        handleRegularMove(piece, xTo, yTo, targetPiece, game);
    }

    private Game findGameById(long gameId) throws ElementNotFoundException {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));
    }

    private Piece findPieceAtPositionForGame(int x, int y, long gameId) throws ElementNotFoundException {
        return pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(x, y, gameId)
                .orElseThrow(() -> new ElementNotFoundException("Piece not found"));
    }

    private void validateMove(Game game, Piece piece, int xTo, int yTo) throws InvalidMoveException {
        GameState requiredGameState = piece.getPlayer().getTeam() == Team.WHITE ? GameState.WHITE_TURN : GameState.BLACK_TURN;
        Coordinate destination = new Coordinate(xTo, yTo);

        if (game.getState() != requiredGameState)
            throw new InvalidMoveException("Invalid move: player is not in turn");
        if (!piece.getLegalMovableSquares().contains(destination))
            throw new InvalidMoveException("Invalid move: not a legal destination square");
    }

    private void handleKingMove(King king, int xTo, int yTo, Game game, Piece targetPiece) {
        king.setHasMoved(true);
        if (abs(king.getHorizontalPosition()-xTo) == 2) {
            castle(king, xTo, yTo, game);
        } else {
            makeMoveAndSaveToHistory(king, xTo, yTo, targetPiece, game);
        }
    }

    private void handlePawnMove(Pawn pawn, int xTo, int yTo, Piece targetPiece, Game game) {
        int promotionRank = pawn.getPlayer().getTeam() == Team.WHITE ? 7 : 0;
        if (yTo == promotionRank) {
            promote(pawn, xTo, yTo, game);
        } else {
            makeMoveAndSaveToHistory(pawn, xTo, yTo, targetPiece, game);
        }
    }

    private void handleRegularMove(Piece piece, int xTo, int yTo, Piece targetPiece, Game game) {
        makeMoveAndSaveToHistory(piece, xTo, yTo, targetPiece, game);
    }

    private void makeMoveAndSaveToHistory(Piece piece, int xTo, int yTo, Piece targetPiece, Game game) {
        targetPiece.ifPresent(pieceRepository::delete);

        piece.setHorizontalPosition(xTo);
        piece.setVerticalPosition(yTo);

        pieceRepository.save(piece);

        saveMoveToRepository(piece, xFrom, yFrom, xTo, yTo, null);
        game.checkState(piece.getPlayer().getTeam());
        gameRepository.save(game);
    }

    private void promote(Pawn pawn, int xTo, int yTo, Game game) {
        pieceRepository.delete(pawn);

        Queen queen = new Queen();
        queen.setPlayer(pawn.getPlayer());
        queen.setHorizontalPosition(xTo);
        queen.setVerticalPosition(yTo);

        pieceRepository.save(queen);
        saveMoveToRepository(pawn, pawn.getHorizontalPosition(), pawn.getVerticalPosition(), xTo, yTo, null);

        game.checkState(pawn.getPlayer().getTeam());
        gameRepository.save(game);
    }

    private void castle(King king, int xTo, int yTo, Game game) {
        CastleType castleType = (xTo > king.getHorizontalPosition()) ? CastleType.SHORT : CastleType.LONG;
        Optional<Piece> optionalRook = getRookForCastling(king, xTo, yTo);

        if (optionalRook.isPresent()) {
            Piece rook = optionalRook.get();
            rook.setHorizontalPosition(xTo > king.getHorizontalPosition() ? xTo - 1 : xTo + 1);
            pieceRepository.save(rook);
        }

        saveMoveToRepository(king, king.getHorizontalPosition(), king.getVerticalPosition(), xTo, yTo, castleType, null);
        king.setHorizontalPosition(xTo);
        king.setVerticalPosition(yTo);
        pieceRepository.save(king);
        game.checkState(king.getPlayer().getTeam());
        gameRepository.save(game);
    }

    private Optional<Piece> getRookForCastling(King king, int xTo, int yTo) {
        return king.getPlayer().getPieces().stream().filter(
                piece -> piece.getVerticalPosition() == yTo && piece.getHorizontalPosition() == (xTo > king.getHorizontalPosition() ? 7 : 0)
        ).findFirst();
    }

    public void saveMoveToRepository(Piece piece, int xFrom, int yFrom, int xTo, int yTo,
                                     CastleType castleType, Piece takenPiece) {
        int moveNumber = piece.getPlayer().getNumberOfMoves() + 1;
        Optional<Move> optionalMove = moveRepository.findByNumberAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(
                moveNumber, xFrom, xTo, yFrom, yTo);
        if (optionalMove.isPresent()) {
            piece.getPlayer().getMoveHistory().add(optionalMove.get());
            playerRepository.save(piece.getPlayer());
        } else {
            Move madeMove = new Move();
            madeMove.setNumber(moveNumber); madeMove.setPiece(piece);
            madeMove.setHorizontalFrom(xFrom); madeMove.setHorizontalTo(xTo);
            madeMove.setVerticalFrom(yFrom); madeMove.setVerticalTo(yTo);
            madeMove.setCastleType(castleType);
            madeMove.setTakenPiece(takenPiece);
            piece.getPlayer().getMoveHistory().add(madeMove);
            playerRepository.save(piece.getPlayer());
            moveRepository.save(madeMove);
            // TODO: taken piece, promoted, check, checkmate
        }
    }
}