package com.practice.project.chess.service.logic.piece;

import com.practice.project.chess.repository.entity.pieces.Piece;
import com.practice.project.chess.service.logic.BoardService;
import com.practice.project.chess.service.logic.MoveOptionService;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class QueenService {

    private final BoardService boardService;
    private final MoveOptionService moveOptionService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        setMovableSquaresWithBoard(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        moveOptionService.addHorizontalMoves(piece, board);
        moveOptionService.addVerticalMoves(piece, board);
        moveOptionService.addDiagonalMoves(piece, board);
    }
}
