package com.ordina.nl.chess.service;

import com.ordina.nl.chess.data.dto.GameDto;
import com.ordina.nl.chess.entity.Game;
import com.ordina.nl.chess.entity.pieces.Piece;
import com.ordina.nl.chess.repository.GameRepository;
import com.ordina.nl.chess.service.structures.BoardMap;
import com.ordina.nl.chess.service.structures.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static com.ordina.nl.chess.constants.BoardSize.horizontalSize;
import static com.ordina.nl.chess.constants.BoardSize.verticalSize;

@AllArgsConstructor
@Service
public class BoardService {

    private final GameRepository gameRepository;

    private final GameService gameService;
    private final PlayerService playerService;

    public BoardMap getBoardMapForGame(GameDto gameDto) {
        BoardMap boardMap = getEmptyBoardMap();
        Game game = gameRepository.findById(gameDto.getId())
                .orElse(null);
        // TODO; when BaseExceptionHandler is put up:
//                .orElseThrow(() -> new ElementNotFoundException("Requested game ID does not correspond to an existing game!"));

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

        for (int i = 0; i < horizontalSize; i++) {
            for (int j = 0; j < verticalSize; j++) {
                boardMap.put(coordinateArray[i][j], null);
            }
        }
        return boardMap;
    }

    private Coordinate[][] getStandardCoordinateArray() {
        Coordinate[][] coordinateArray = new Coordinate[horizontalSize][verticalSize];
        for (int i = 0; i < horizontalSize; i++) {
            for (int j = 0; j < verticalSize; j++) {
                coordinateArray[i][j] = new Coordinate(i, j);
            }
        }
        return coordinateArray;
    }

    private void addPiecesToBoard(List<Piece> pieces, BoardMap boardMap) {
        for (Piece piece : pieces) {
            boardMap.put(boardMap.getCoordinateByPos(piece.getHorizontalPosition(), piece.getVerticalPosition()),
                    piece);
        }
    }
}
