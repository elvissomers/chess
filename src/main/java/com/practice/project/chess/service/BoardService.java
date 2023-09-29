package com.practice.project.chess.service;

import com.practice.project.chess.repository.entity.Game;
import com.practice.project.chess.repository.entity.pieces.Piece;
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

    public BoardMap getBoardMapForGame(long gameId) {
        BoardMap boardMap = getEmptyBoardMap();
        Game game = gameRepository.findById(gameId)
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

        for (int i = 0; i < BoardSize.horizontalSize; i++) {
            for (int j = 0; j < BoardSize.verticalSize; j++) {
                boardMap.put(coordinateArray[i][j], null);
            }
        }
        return boardMap;
    }

    private Coordinate[][] getStandardCoordinateArray() {
        Coordinate[][] coordinateArray = new Coordinate[BoardSize.horizontalSize][BoardSize.verticalSize];
        for (int i = 0; i < BoardSize.horizontalSize; i++) {
            for (int j = 0; j < BoardSize.verticalSize; j++) {
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

    public BoardMap getBoardMapForCopiedPiece(Piece originalPiece, Piece copyPiece, long gameId) {
        BoardMap board = getBoardMapForGame(gameId);

        board.put(board.getCoordinateByPos(originalPiece.getHorizontalPosition(), originalPiece.getVerticalPosition()), null);
        board.put(board.getCoordinateByPos(copyPiece.getHorizontalPosition(), copyPiece.getVerticalPosition()), copyPiece);

        return board;
    }
}
