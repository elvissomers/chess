package com.practice.project.chess.service.model;

import com.practice.project.chess.service.model.movehistory.Move;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.Team;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO: players should be connected to accounts

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Player {

    private long id;
    private Team team;

    private List<Piece> pieces = new ArrayList<>();
    private List<Move> moveHistory = new ArrayList<>();
}