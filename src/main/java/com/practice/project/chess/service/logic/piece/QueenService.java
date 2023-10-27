package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.MoveOptionUtil;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class QueenService {

    private final BoardService boardService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        setMovableSquaresWithBoard(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        MoveOptionUtil.addHorizontalMoves(piece, board);
        MoveOptionUtil.addVerticalMoves(piece, board);
        MoveOptionUtil.addDiagonalMoves(piece, board);
    }
}
