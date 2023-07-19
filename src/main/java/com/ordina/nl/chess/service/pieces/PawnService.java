package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.data.dto.PieceDto;
import com.ordina.nl.chess.data.dto.SquaresDto;
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

import static com.ordina.nl.chess.constants.BoardSize.horizontalSize;

@AllArgsConstructor
@Service
public class PawnService {

    private final PieceRepository pieceRepository;

    private final GameService gameService;
    private final BoardService boardService;

    private int xPos;
    private int yPos;
    private int startPos;
    private int yDirection;

    public SquaresDto getAttackedSquares(PieceDto pieceDto) {
        return SquaresDto.builder().squares(
                pieceRepository.findById(pieceDto.getId())
                        .map(Piece::getAttackedSquares)
                        .orElse(null)) // TODO: .orElseThrow
                .build();
    }

    public void setMovableSquares(Piece piece, long gameId) {
        setPawnBasicMoves(piece, gameId);
        setPawnEnPassantMoves(piece, gameId);
    }
    
    public void setPawnBasicMoves(Piece pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        obtainPawnPosition(pawn);

        Coordinate squareInFront = board.getCoordinateArray()[xPos][yPos + yDirection];
        if (board.get(squareInFront) == null) {
            pawn.addMovableSquare(squareInFront);
        }

        if (xPos + 1 < horizontalSize) {
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

    private void obtainPawnPosition(Piece pawn) {
        xPos = pawn.getHorizontalPosition();
        yPos = pawn.getVerticalPosition();

        yDirection = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : -1;
        startPos = (pawn.getPlayer().getTeam() == Team.WHITE) ? 1 : 6;
    }

    public void setPawnEnPassantMoves(Piece pawn, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        Game game = gameService.getGame(gameId);
        obtainPawnPosition(pawn);

        if (yPos == startPos + 3 * yDirection) {
            if (xPos > 0 && board.get(board.getCoordinateArray()[xPos - 1][yPos]) instanceof Pawn otherPawn &&
                    otherPawn.getPlayer().getTeam() != pawn.getPlayer().getTeam()) {
                if (pawnCanBeTakenEnPassantByPawn(otherPawn, (Pawn) pawn, game)) {
                    Coordinate squareInFrontLeft = board.getCoordinateArray()[xPos - 1][yPos + yDirection];
                    pawn.addMovableSquare(squareInFrontLeft);
                }
            }
            if (xPos + 1 < horizontalSize && board.get(board.getCoordinateArray()[xPos + 1][yPos]) instanceof Pawn otherPawn &&
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

    private boolean pawnCanBeTakenEnPassantByPawn(Pawn targetPawn, Pawn attackingPawn, Game game) {
        Player otherPlayer = (targetPawn.getPlayer().getTeam() == Team.WHITE) ? game.getBlackPlayer()
                : game.getWhitePlayer();
        return (otherPlayer.getLastMove().getPiece() == targetPawn && otherPlayer.getLastMove().getVerticalFrom()
                == ((attackingPawn.getPlayer().getTeam() == Team.BLACK) ? 1 : 6));
    }
    
    
}
