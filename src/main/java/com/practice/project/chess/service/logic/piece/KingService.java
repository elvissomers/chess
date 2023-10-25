package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.service.logic.AllUtil;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KingService {

    private final BoardService boardService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);

        setKingBasicMoves(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        setKingBasicMoves(piece, board);
    }

    private void setKingBasicMoves(Piece piece, BoardMap board){
        int xPos = piece.getCoordinate().getXPos();
        int yPos = piece.getCoordinate().getYPos();

        for (int x = xPos-1; x <= xPos+1; x++) {
            for (int y = yPos-1; y <= yPos+1; y++) {
                if (AllUtil.withinBoard(x, y)) {
                    AllUtil.addMovableSquareIfEmptyOrEnemy(x, y, piece, board);
                }
            }
        }
    }
}
