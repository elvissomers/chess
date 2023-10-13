package com.practice.project.chess.service.logic;

import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.model.mapper.GameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
        Mockito.when(boardService.get).

        // Act
        boardService.getBoardMapForGame(mockId);


    }

}