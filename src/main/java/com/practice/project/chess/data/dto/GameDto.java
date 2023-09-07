package com.practice.project.chess.data.dto;

import com.practice.project.chess.enums.GameState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private long whitePlayerId;
    private long blackPlayerId;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    private List<PieceDto> pieces;

}
