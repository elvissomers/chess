package com.practice.project.chess.service.pieces;

import com.practice.project.chess.entity.pieces.Piece;
import com.practice.project.chess.service.BoardService;
import com.practice.project.chess.service.MoveOptionService;
import com.practice.project.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RookService {

    private final BoardService boardService;
    private final MoveOptionService moveOptionService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        moveOptionService.addHorizontalMoves(piece, board);
        moveOptionService.addVerticalMoves(piece, board);
    }

    public void setMovableSquaresWithBoard(Piece piece, BoardMap board) {
        moveOptionService.addHorizontalMoves(piece, board);
        moveOptionService.addVerticalMoves(piece, board);
    }
}
