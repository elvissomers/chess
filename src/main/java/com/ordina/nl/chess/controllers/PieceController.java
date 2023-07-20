package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.data.dto.GetPieceDataDto;
import com.ordina.nl.chess.data.dto.SquaresDto;
import com.ordina.nl.chess.data.dto.MovePieceDto;
import com.ordina.nl.chess.data.dto.MovePieceResponseDto;
import com.ordina.nl.chess.entity.Move;
import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import com.ordina.nl.chess.service.MoveOptionService;
import com.ordina.nl.chess.entity.pieces.*;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.repository.MoveRepository;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.repository.PlayerRepository;
import com.ordina.nl.chess.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.Math.abs;

@RestController
@RequestMapping("piece")
@CrossOrigin(maxAge = 3600)
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
    private MoveOptionService moveFinder;

    @GetMapping("get_movable_squares")
    public SquaresDto getMovableSquares(@RequestBody GetPieceDataDto dto) {
        Optional<Game> optionalGame = gameRepository.findById(dto.getGameId());
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                dto.getxPos(), dto.getyPos(), dto.getGameId());

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return new SquaresDto(null);
        BoardMap board = moveFinder.setBoardMap(optionalGame.get());
        optionalGame.get().setMovableSquaresForPiece(optionalPiece.get(), board);

        return new SquaresDto(optionalPiece.get().getLegalMovableSquares());
    }

    @PutMapping("move")
    public MovePieceResponseDto makeMove(@RequestBody MovePieceDto dto) {
        long gameId = dto.getGameId();
        int xFrom = dto.getxFrom();
        int yFrom = dto.getyFrom();
        int xTo = dto.getxTo();
        int yTo = dto.getyTo();

        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xFrom, yFrom, gameId);
        Optional<Piece> optionalTakenPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xTo, yTo, gameId);

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return new MovePieceResponseDto("Error: Not an existing piece or game!");
        Piece piece = optionalPiece.get();
        BoardMap board = moveFinder.setBoardMap(optionalGame.get());
        optionalGame.get().setMovableSquaresForPiece(piece, board);

        GameState neededGameState = (piece.getPlayer().getTeam() == Team.WHITE) ? GameState.WHITE_TURN
                : GameState.BLACK_TURN;
        Coordinate destination = new Coordinate(xTo, yTo);
        if (optionalGame.get().getState() != neededGameState || !piece
                .getLegalMovableSquares().contains(destination))
            return new MovePieceResponseDto("Error: Not a legal move or player is not in turn!");
        
        if (piece instanceof King king) {
            king.setHasMoved(true);
            if (abs(xFrom-xTo) == 2) {
                castle(king, xTo, yTo, optionalGame.get());
                return new MovePieceResponseDto("Castled successfully");
            }
        }

        int promotionRank = (piece.getPlayer().getTeam() == Team.WHITE) ? 7 : 0;
        if (piece instanceof Pawn pawn && yTo == promotionRank) {
            promote(pawn, xTo, yTo, optionalTakenPiece.orElse(null));
            return new MovePieceResponseDto("Promoted successfully");
        }

        optionalTakenPiece.ifPresent(pieceRepository::delete);

        piece.setHorizontalPosition(xTo);
        piece.setVerticalPosition(yTo);
        pieceRepository.save(piece);
        
        saveMoveToRepository(piece, xFrom, yFrom, xTo, yTo, null, optionalTakenPiece.orElse(null));
        optionalGame.get().checkState(piece.getPlayer().getTeam());
        gameRepository.save(optionalGame.get());
        return new MovePieceResponseDto("Made move successfully");
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
            madeMove.setNumber(moveNumber);
            madeMove.setPiece(piece);
            madeMove.setHorizontalFrom(xFrom);
            madeMove.setHorizontalTo(xTo);
            madeMove.setVerticalFrom(yFrom);
            madeMove.setVerticalTo(yTo);
            madeMove.setCastleType(castleType);
            madeMove.setTakenPiece(takenPiece);
            piece.getPlayer().getMoveHistory().add(madeMove);
            playerRepository.save(piece.getPlayer());
            moveRepository.save(madeMove);
            // TODO: check, checkmate
        }
    }

    public void promote(Pawn pawn, int xTo, int yTo, Piece takenPiece) {
        // TODO: promotion! Player should be able to choose!
        pieceRepository.delete(pawn);
        Queen queen = new Queen();
        queen.setPlayer(pawn.getPlayer());
        queen.setHorizontalPosition(xTo);
        queen.setVerticalPosition(yTo);
        pieceRepository.save(queen);
        saveMoveToRepository(pawn, pawn.getHorizontalPosition(), pawn.getVerticalPosition(), xTo, yTo,
                null, takenPiece);
    }
    
    public void castle(King king, int xTo, int yTo, Game game) {
        CastleType castleType;

        if (xTo > king.getHorizontalPosition()) {
            castleType = CastleType.SHORT;
            Optional<Piece> optionalRook = king.getPlayer().getPieces().stream()
                    .filter(piece ->
                            piece.getVerticalPosition() == yTo && piece.getHorizontalPosition() == 7)
                    .findFirst();

            if (optionalRook.isPresent()) {
                Rook rook = (Rook) optionalRook.get();
                rook.setHorizontalPosition(xTo - 1);
                rook.setHasMoved(true);
                pieceRepository.save(rook);
            }
        }
        else {
            castleType = CastleType.LONG;
            Optional<Piece> optionalRook = king.getPlayer().getPieces().stream()
                    .filter(piece ->
                            piece.getVerticalPosition() == yTo && piece.getHorizontalPosition() == 0)
                    .findFirst();

            if (optionalRook.isPresent()) {
                Rook rook = (Rook) optionalRook.get();
                rook.setHorizontalPosition(xTo + 1);
                rook.setHasMoved(true);
                pieceRepository.save(rook);
            }
        }
        saveMoveToRepository(king, king.getHorizontalPosition(), king.getVerticalPosition(), xTo, yTo,
                castleType, null);

        king.setHorizontalPosition(xTo);
        king.setVerticalPosition(yTo);
        pieceRepository.save(king);

        game.checkState(king.getPlayer().getTeam());
        gameRepository.save(game);
    }
}
