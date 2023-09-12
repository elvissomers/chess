package com.practice.project.chess.service;

import com.practice.project.chess.data.dto.MoveDto;
import com.practice.project.chess.data.dto.mapper.MoveDtoMapper;
import com.practice.project.chess.entity.Move;
import com.practice.project.chess.repository.MoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MoveService {

    private final MoveRepository moveRepository;

    private final MoveDtoMapper moveDtoMapper;

    public Move getMove(long id) {
        return moveRepository.findById(id)
                .orElse(null);
        // TODO .orElseThrow(ElementNotFoundException)
    }

    public MoveDto getMoveDto(long id) {
        return moveRepository.findById(id)
                .map(moveDtoMapper::MoveToMoveDto)
                .orElse(null);
        // TODO .orElseThrow(ElementNotFoundException)
    }

    public List<MoveDto> getMovesFromPlayerId(long id) {
        return moveRepository.findByPlayers_IdContaining(id).stream()
                .map(moveDtoMapper::MoveToMoveDto)
                .toList();
    }

    public Move createMove() {
        //todo
        return null;
    }
}
