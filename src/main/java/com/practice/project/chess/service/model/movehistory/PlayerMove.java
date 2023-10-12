package com.practice.project.chess.service.model.movehistory;

import com.practice.project.chess.service.model.Player;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PlayerMove {

    private long id;

    private Player player;
    private Move move;

    private int number;
}
