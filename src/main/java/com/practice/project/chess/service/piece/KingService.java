package com.practice.project.chess.service.piece;

import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KingService {

    private final BoardService boardService;
    private final MoveOptionService moveOptionService;

    private int xPos;
    private int yPos;

    public void setMovableSquares(Piece piece, long gameId) {
        getPosition(piece);
        BoardMap board = boardService.getBoardMapForGame(gameId);

        setKingBasicMoves(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        getPosition(piece);
        setKingBasicMoves(piece, board);
    }

    private void getPosition(Piece piece) {
        xPos = piece.getHorizontalPosition();
        yPos = piece.getVerticalPosition();
    }

    private void setKingBasicMoves(Piece piece, BoardMap board){
        for (int x = xPos-1; x <= xPos+1; x++) {
            for (int y = yPos-1; y <= yPos+1; y++) {
                if (moveOptionService.withinBoard(x, y)) {
                    MoveOptionService.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
                }
            }
        }
    }
}
