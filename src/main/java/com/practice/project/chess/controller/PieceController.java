package com.practice.project.chess.controller;

import com.practice.project.chess.controller.dto.SquaresDto;
import com.practice.project.chess.service.piece.PieceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("piece")
@CrossOrigin(maxAge = 3600)
public class PieceController {

    private final PieceService pieceService;

    @GetMapping("movable_squares")
    private SquaresDto getMovableSquaresForPiece() {
        // TODO
        return null;
    }
}
