package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.MoveOptionUtil;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BishopService {

    private final BoardService boardService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        MoveOptionUtil.addDiagonalMoves(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        MoveOptionUtil.addDiagonalMoves(piece, board);
    }
}
