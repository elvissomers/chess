package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.MoveDto;
import com.ordina.nl.chess.data.dto.mapper.MoveDtoMapper;
import com.ordina.nl.chess.entity.Move;
import com.ordina.nl.chess.repository.MoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
