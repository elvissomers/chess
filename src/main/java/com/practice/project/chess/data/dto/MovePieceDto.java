package com.practice.project.chess.data.dto;

public class MovePieceDto {

    private long gameId;

    private int xFrom;

    private int yFrom;

    private int xTo;

    private int yTo;

    public long getGameId() {
        return gameId;
    }

    public int getxFrom() {
        return xFrom;
    }

    public int getyFrom() {
        return yFrom;
    }

    public int getxTo() {
        return xTo;
    }

    public int getyTo() {
        return yTo;
    }
}
