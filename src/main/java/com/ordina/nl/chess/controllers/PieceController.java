package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.structures.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PieceController {

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private GameRepository gameRepository;

    // This is a get mapping, it should not change anything
    public List<Coordinate> getMovableSquares(long gameId, int xPos, int yPos){
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xPos, yPos, gameId);

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return null;
        optionalGame.get().setMovableSquaresForPiece(optionalPiece.get());

        // TODO: to DTO
        return optionalPiece.get().getLegalMovableSquares();
    }
}
