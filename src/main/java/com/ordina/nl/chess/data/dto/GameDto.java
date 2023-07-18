package com.ordina.nl.chess.data.dto;

import com.ordina.nl.chess.enums.GameState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private long id;
    private long whitePlayerId;
    private long blackPlayerId;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

}
