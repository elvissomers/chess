//package com.ordina.nl.chess.controllers;
//
//import com.ordina.nl.chess.dto.MovePieceDto;
//import com.ordina.nl.chess.game.Move;
//import com.ordina.nl.chess.instances.Game;
//import com.ordina.nl.chess.pieces.Piece;
//import com.ordina.nl.chess.structures.Coordinate;
//import com.ordina.nl.chess.structures.Team;
//
//import java.util.List;
//
//public class PlayGame {
//
//    private Team playerTurn;
//
//    private Game game;
//
//    @GetRequest
//    private List<Coordinate> getMovableCoordinatesForPiece(int i, int j){
//        return game.getBoard().getPieceByPos(i,j).getMovableSquares();
//    }
//
//    @PutRequest
//    private void movePiece(MovePieceDto movePieceDto){
//        Piece piece = game.getBoard().getPieceByPos(movePieceDto.getxFrom(), movePieceDto.getyFrom());
//        Coordinate destination = game.getBoard().getCoordinateByPos(movePieceDto.getxTo(), movePieceDto.getyTo());
//        Move move = game.getMoveMaker().getMove(game, piece, destination);
//        game.getMoveMaker().makeMove(game, move);
//        game.update(piece.getPlayer().getTeam());
//    }
//
//}
