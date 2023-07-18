package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.entity.Player;
import com.ordina.nl.chess.entity.pieces.Pawn;
import com.ordina.nl.chess.entity.pieces.Piece;
import com.ordina.nl.chess.enums.Team;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.repository.PieceRepository;
import com.ordina.nl.chess.service.BoardService;
import com.ordina.nl.chess.service.GameService;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PawnService {

    private final PieceRepository pieceRepository;
    private final GameRepository gameRepository;

    private final GameService gameService;
    private final BoardService boardService;


    
    public void setPawnBasicMoves(Piece pawn, BoardMap board) {
        int xPos = pawn.getHorizontalPosition();
        int yPos = pawn.getVerticalPosition();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        Coordinate squareInFront = board.getCoordinateArray()[xPos][yPos + yDirection];
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < xSize) {
            Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
            if (board.get(squareInFrontRight) != null && board.get(squareInFrontRight).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontRight);
            }
        }

        if (xPos > 0) {
            Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
            if (board.get(squareInFrontLeft) != null && board.get(squareInFrontLeft).
                    getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                pawn.addMovableSquare(squareInFrontLeft);
            }
        }

        if (yPos == startPos) {
            Coordinate squareTwoInFront = board.getCoordinateArray()[xPos][yPos + 2 * yDirection];
            if (board.get(squareTwoInFront) == null) {
                pawn.addMovableSquare(squareTwoInFront);
            }
        }
    }

    public void setPawnEnPassantMoves(Piece pawn, BoardMap board, Game game) {
        int xPos = pawn.getHorizontalPosition();
        int yPos = pawn.getVerticalPosition();

        int yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        int startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.get(board.getCoordinateArray()[xPos - 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < xSize && board.get(board.getCoordinateArray()[xPos + 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                    {
                        Coordinate squareInFrontRight = board.getCoordinateArray()[xPos + 1][yPos + yDirection];
                        pawn.addMovableSquare(squareInFrontRight);
                    }
                }
            }
        }
    }

    public boolean pawnCanBeTakenEnPassantByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player otherPlayer = (targetPawn.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        return (otherPlayer.getLastMove().getPiece() == targetPawn && otherPlayer.getLastMove().getVerticalFrom()
                == ((attackingPawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6));
    }
    
    
}
