package com.practice.project.chess.service.logic;

import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.repository.dao.GameDao;
import com.practice.project.chess.service.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private BoardService subject;

    @Test
    void getBoardMapForGame() {
        // Given
        int mockId = 1;
        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Mockito.mock(Game.class)));
        // When
        subject.getBoardMapForGame(mockId);

        // Then

    }

    @Test
    void getBoardMapForCopiedPiece() {
    }
}