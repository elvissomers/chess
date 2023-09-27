package com.practice.project.chess.exception;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String message) {
        super(message);
    }
}
