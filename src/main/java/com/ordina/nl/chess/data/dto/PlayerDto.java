package com.ordina.nl.chess.data.dto;

import com.ordina.nl.chess.enums.Team;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private long id;
    private long gameId;

    @Enumerated(EnumType.STRING)
    private Team team;
}
