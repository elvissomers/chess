package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.service.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerMapper {

    private final PieceMapper pieceMapper;

    public Player playerDaoToPlayer(PlayerDao dao) {
        return Player.builder()
                .id(dao.getId())
                .team(dao.getTeam())
                .pieces(dao.getPieces().stream()
                        .map(pieceMapper::daoToPiece)
                        .toList())
                .build();
    }
}
