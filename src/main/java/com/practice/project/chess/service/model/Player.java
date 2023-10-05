package com.practice.project.chess.service.model;

import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.repository.enums.Team;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// TODO: players should be connected to accounts

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Team team;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<Piece> pieces = new ArrayList<>();
}