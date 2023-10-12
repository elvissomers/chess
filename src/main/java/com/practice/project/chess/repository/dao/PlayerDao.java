package com.practice.project.chess.repository.dao;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.Team;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayerDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Team team;

    @OneToMany
    private List<PieceDao> pieces = new ArrayList<>();

    @OneToMany
    private List<PlayerMoveDao> moveHistory = new ArrayList<>();
}