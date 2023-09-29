package com.practice.project.chess.service.exception;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String message) {
        super(message);
    }
}
