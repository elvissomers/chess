package com.practice.project.chess.service.model;

import com.practice.project.chess.repository.dao.Player;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerMove {

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
