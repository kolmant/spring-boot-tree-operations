package com.masivian.example.treeoperations.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ InconsistentOperationException.class, EntityNotFoundException.class })
    public final ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

@AllArgsConstructor
class ExceptionMessage {
    public String message;
}
