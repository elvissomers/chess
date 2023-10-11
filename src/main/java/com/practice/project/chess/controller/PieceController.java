package com.practice.project.chess.controller;

import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.repository.filling.FillPieceRepositoryService;
import com.practice.project.chess.service.logic.piece.PieceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("piece")
@CrossOrigin(maxAge = 3600)
public class PieceController {

    private final PieceService pieceService;
    private final FillPieceRepositoryService fillPieceRepositoryService;

    @PutMapping("fill_repo")
    private void fillPieceRepository() {
        fillPieceRepositoryService.fillPieceRepository();
    }

    @GetMapping("movable_squares")
    private SquaresDto getMovableSquaresForPiece() {
        // TODO
        return null;
    }
}
