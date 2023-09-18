package com.practice.project.chess.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerMove {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @ManyToOne
    private Player player;

    @Column
    @ManyToOne
    private Move move;

    @Column
    private int number;
}
