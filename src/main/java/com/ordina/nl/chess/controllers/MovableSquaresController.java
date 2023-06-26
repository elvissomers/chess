package com.ordina.nl.chess.controllers;

import com.ordina.nl.chess.instances.Game;
import com.ordina.nl.chess.pieces.Piece;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.structures.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovableSquaresController {

    @Autowired
    PieceRepository pieceRepository;

    @Autowired
    GameRepository gameRepository;

    public Set<Coordinate> getMovableSquares(long gameId, int xPos, int yPos){
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<Piece> optionalPiece = pieceRepository.findByHorizontalPositionAndVerticalPositionAndPlayer_Game_Id(
                xPos, yPos, gameId);

        if (optionalGame.isEmpty() || optionalPiece.isEmpty())
            return null;

        // TODO!
        optionalGame.get().setMovableSquaresForPiece(optionalPiece.get());

        Set<Coordinate> movableSquares = optionalPiece.get().getLegalMovableSquares();
        // TODO: to DTO

        return movableSquares;
    }
}
