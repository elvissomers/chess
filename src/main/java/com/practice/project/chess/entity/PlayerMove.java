package com.practice.project.chess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerMove {

    private long id;

    @Column
    private Player player;

    @Column
    private Move move;

    @Column
    private int number;
}
