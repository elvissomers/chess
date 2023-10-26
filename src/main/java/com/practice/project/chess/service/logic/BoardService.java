package com.practice.project.chess.service.logic;

import com.practice.project.chess.service.model.Game;
import com.practice.project.chess.service.model.mapper.GameMapper;
import com.practice.project.chess.service.model.pieces.Piece;
import com.practice.project.chess.service.exception.ElementNotFoundException;
import com.practice.project.chess.repository.GameRepository;
import com.practice.project.chess.service.structures.BoardMap;
import com.practice.project.chess.service.structures.Coordinate;
import com.practice.project.chess.service.constants.BoardSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class BoardService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public BoardMap getBoardMapForGame(long gameId) {
        // TODO: game instead of gameId? That would make this class stateless, and we can make it an util class
        BoardMap boardMap = getEmptyBoardMap();
        Game game = gameRepository.findById(gameId)
                .map(gameMapper::gameDaoToGame)
                .orElseThrow(() -> new ElementNotFoundException("Game not found!"));

        if (game != null) {
            List<Piece> pieces = Stream.concat(game.getWhitePlayer().getPieces().stream(),
                            game.getBlackPlayer().getPieces().stream())
                    .toList();
            addPiecesToBoard(pieces, boardMap);
        }
        return boardMap;
    }

    private BoardMap getEmptyBoardMap() {
        Coordinate[][] coordinateArray = getStandardCoordinateArray();
        BoardMap boardMap = new BoardMap(coordinateArray);

        for (int i = 0; i < BoardSize.HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < BoardSize.VERTICAL_SIZE; j++) {
                boardMap.put(coordinateArray[i][j], null);
            }
        }
        return boardMap;
    }

    private Coordinate[][] getStandardCoordinateArray() {
        Coordinate[][] coordinateArray = new Coordinate[BoardSize.HORIZONTAL_SIZE][BoardSize.VERTICAL_SIZE];
        for (int i = 0; i < BoardSize.HORIZONTAL_SIZE; i++) {
            for (int j = 0; j < BoardSize.VERTICAL_SIZE; j++) {
                coordinateArray[i][j] = new Coordinate(i, j);
            }
        }
        return coordinateArray;
    }

    private void addPiecesToBoard(List<Piece> pieces, BoardMap boardMap) {
        for (Piece piece : pieces) {
            boardMap.put(piece.getCoordinate(), piece);
        }
    }

    public BoardMap getBoardMapForCopiedPiece(Piece originalPiece, Piece copyPiece, long gameId) {
        BoardMap board = getBoardMapForGame(gameId);

        board.put(originalPiece.getCoordinate(), null);
        board.put(copyPiece.getCoordinate(), copyPiece);

        return board;
    }
}
