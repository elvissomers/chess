package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.PlayerMoveDao;
import com.practice.project.chess.service.model.movehistory.PlayerMove;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerMoveMapper {

    private final MoveMapper moveMapper;
    private final PlayerMapper playerMapper;

    public PlayerMove daoToPlayerMove(PlayerMoveDao dao) {
        // TODO: this will not work because it will result in circular dependencies. OR is there another way to circumvent that?
        return PlayerMove.builder()
                .move(moveMapper.daoToMove(dao.getMove()))
                .id(dao.getId())
                .number(dao.getNumber())
                .player(playerMapper.daoToPlayer(dao.getPlayer())) // But this will be a circular dependency!
                .build();
    }
}
