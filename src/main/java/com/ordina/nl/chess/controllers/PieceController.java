package com.ordina.nl.chess.controllers;

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

public class PieceController {

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

    // This is a put mapping, it should update the game to make a move
    public void makeMove(long gameId, int xFrom, int yFrom, int xTo, int yTo) {
        // TODO: return a status repsponse DTO instead of void
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xFrom, yFrom, gameId);
        Optional<Piece> optionalTakenPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xTo, yTo, gameId);

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return; // TODO
        Piece piece = optionalPiece.get();
        BoardMap board = moveFinder.setBoardMap(optionalGame.get());
        optionalGame.get().setMovableSquaresForPiece(piece, board);

        GameState neededGameState = (piece.getPlayer().getTeam() == Team.WHITE) ? GameState.WHITE_TURN
                : GameState.BLACK_TURN;
        Coordinate destination = new Coordinate(xTo, yTo);
        if (optionalGame.get().getState() != neededGameState || !piece
                .getLegalMovableSquares().contains(destination))
            return; // TODO
        
        if (piece instanceof King king) {
            king.setHasMoved(true);
            if (abs(xFrom-xTo) == 2) {
                castle(king, xTo, yTo, optionalGame.get());
                return;
            }
        }

        int promotionRank = (piece.getPlayer().getTeam() == Team.WHITE) ? 7 : 0;
        if (piece instanceof Pawn pawn && yTo == promotionRank) {
            promote(pawn, xTo, yTo);
            return;
        }

        optionalTakenPiece.ifPresent(pieceRepository::delete);

        piece.setHorizontalPosition(xTo); piece.setVerticalPosition(yTo);
        pieceRepository.save(piece); // Is this needed? I am not sure?
        
        saveMoveToRepository(piece, xFrom, yFrom, xTo, yTo, null);
        optionalGame.get().checkState(piece.getPlayer().getTeam());
        gameRepository.save(optionalGame.get());
    }
    
    public void saveMoveToRepository(Piece piece, int xFrom, int yFrom, int xTo, int yTo, CastleType castleType) {
        int moveNumber = piece.getPlayer().getNumberOfMoves() + 1;
        Optional<Move> optionalMove = moveRepository.findByNumberAndHorizontalFromAndHorizontalToAndVerticalFromAndVerticalTo(
                moveNumber, xFrom, xTo, yFrom, yTo);
        if (optionalMove.isPresent()) {
            piece.getPlayer().getMoveHistory().add(optionalMove.get());
            playerRepository.save(piece.getPlayer());
        } else {
            Move madeMove = new Move();
            madeMove.setNumber(moveNumber);
            madeMove.setHorizontalFrom(xFrom); madeMove.setHorizontalTo(xTo);
            madeMove.setVerticalFrom(yFrom); madeMove.setVerticalTo(yTo);
            madeMove.setCastleType(castleType);
            piece.getPlayer().getMoveHistory().add(madeMove);
            playerRepository.save(piece.getPlayer());
            moveRepository.save(madeMove);
            // TODO: taken piece, promoted, check, checkmate
        }
    }

    public void promote(Pawn pawn, int xTo, int yTo) {
        // TODO: promotion! Player should be able to choose!
        pieceRepository.delete(pawn);
        Queen queen = new Queen();
        queen.setPlayer(pawn.getPlayer());
        queen.setHorizontalPosition(xTo);
        queen.setVerticalPosition(yTo);
        pieceRepository.save(queen);
        saveMoveToRepository(pawn, pawn.getHorizontalPosition(), pawn.getVerticalPosition(), xTo, yTo, null);
    }
    
    public void castle(King king, int xTo, int yTo, Game game) {
        CastleType castleType;
        // TODO: set has Moved attribute of rook

        if (xTo > king.getHorizontalPosition()) {
            castleType = CastleType.SHORT;
            Optional<Piece> optionalRook = king.getPlayer().getPieces().stream().filter(
                    piece -> piece.getVerticalPosition() == yTo && piece.getHorizontalPosition() == 7
            ).findFirst();

            if (optionalRook.isPresent()) {
                Piece rook = optionalRook.get();
                rook.setHorizontalPosition(xTo - 1);
                pieceRepository.save(rook);
            }
        }
        else {
            castleType = CastleType.LONG;
            Optional<Piece> optionalRook = king.getPlayer().getPieces().stream().filter(
                    piece -> piece.getVerticalPosition() == yTo && piece.getHorizontalPosition() == 0
            ).findFirst();

            if (optionalRook.isPresent()) {
                Piece rook = optionalRook.get();
                rook.setHorizontalPosition(xTo + 1);
                pieceRepository.save(rook);
            }
        }
        saveMoveToRepository(king, king.getHorizontalPosition(), king.getVerticalPosition(), xTo, yTo, castleType);
        king.setHorizontalPosition(xTo); king.setVerticalPosition(yTo);
        pieceRepository.save(king);

        game.checkState(king.getPlayer().getTeam());
        gameRepository.save(game);
    }
}
