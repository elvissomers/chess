package com.ordina.nl.chess.service.pieces;

import com.ordina.nl.chess.entity.pieces.Piece;
import com.ordina.nl.chess.service.BoardService;
import com.ordina.nl.chess.service.MoveOptionService;
import com.ordina.nl.chess.service.structures.BoardMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BishopService {

    private final BoardService boardService;
    private final MoveOptionService moveOptionService;

    public void setMovableSquares(Piece piece, long gameId) {
        BoardMap board = boardService.getBoardMapForGame(gameId);
        moveOptionService.addDiagonalMoves(piece, board);
    }
}
