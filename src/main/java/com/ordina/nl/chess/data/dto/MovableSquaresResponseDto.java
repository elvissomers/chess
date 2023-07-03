package com.ordina.nl.chess.data.dto;

import com.ordina.nl.chess.structures.Coordinate;

import java.util.List;

public class MovableSquaresResponseDto {

    private String message;

    private List<Coordinate> movableSquares;

    public MovableSquaresResponseDto(String message) {
        this.message = message;
    }

    public List<Coordinate> getMovableSquares() {
        return movableSquares;
    }

    public void setMovableSquares(List<Coordinate> movableSquares) {
        this.movableSquares = movableSquares;
    }
}
