package com.practice.project.chess.repository.dao;

import com.practice.project.chess.service.model.Move;
import com.practice.project.chess.repository.entity.Player;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerMoveDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Move move;

    @Column
    private int number;
}