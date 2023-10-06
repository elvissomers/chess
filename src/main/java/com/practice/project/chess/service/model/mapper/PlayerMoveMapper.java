package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.service.model.movehistory.PlayerMove;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerMoveMapper {

    private final MoveMapper moveMapper;

    public PlayerMove daoToPlayerMove() {
        // TODO: this will not work because it will result in circular dependencies. OR is there another way to circumvent that?
        return null;
    }
}
