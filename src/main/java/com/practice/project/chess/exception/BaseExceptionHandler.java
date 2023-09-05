package com.practice.project.chess.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {

    String INTERNAL_SERVER_ERROR = "Internal Server Error";
    Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ElementNotFoundException exception) {
        log.error(exception.toString());
        var errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
