package com.practice.project.chess.service.model.mapper;

import com.practice.project.chess.repository.dao.PlayerDao;
import com.practice.project.chess.repository.dao.PlayerMoveDao;
import com.practice.project.chess.service.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@AllArgsConstructor
public class PlayerMapper {

    private final PieceMapper pieceMapper;
    private final MoveMapper moveMapper;

    public Player daoToPlayer(PlayerDao dao) {
        return Player.builder()
                .id(dao.getId())
                .team(dao.getTeam())
                .pieces(dao.getPieces().stream()
                        .map(pieceMapper::daoToPiece)
                        .toList())
                .moveHistory(dao.getMoveHistory().stream()
                        .sorted(Comparator.comparingInt(PlayerMoveDao::getNumber))
                        .map(PlayerMoveDao::getMove)
                        .map(moveMapper::daoToMove)
                        .toList())
                .build();
    }

}
