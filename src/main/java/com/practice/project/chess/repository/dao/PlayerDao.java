package com.practice.project.chess.repository.dao;

import com.practice.project.chess.repository.dao.pieces.PieceDao;
import com.practice.project.chess.repository.enums.Team;
import com.practice.project.chess.service.model.Game;
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

    @OneToOne
    // TODO: should player refer to game? OR only the other way around? separation of concerns says the second
    private Game game;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Team team;

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<PieceDao> pieces = new ArrayList<>();
}