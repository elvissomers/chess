package com.practice.project.chess.service.logic;

import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.model.mapper.GameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private BoardService boardService;

    @Test
    void test1() {
        // Arrange
        long mockId = 1;

        // Act
//        boardService.getBoardMapForGame(mockId);


    }

}