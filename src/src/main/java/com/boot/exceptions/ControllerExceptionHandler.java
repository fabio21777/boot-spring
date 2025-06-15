package com.boot.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("ControllerExceptionHandler");

    @ExceptionHandler(NotFoundService.class)
    public ResponseEntity<StandardError> entityNotFound(NotFoundService e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> notvalid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus 	status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("erro validation");
        error.setPath(request.getRequestURI());
        for(FieldError fieldMessage : e.getBindingResult().getFieldErrors() ) {
            error.addError(fieldMessage.getField(),fieldMessage.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ServiceError.class)
    public ResponseEntity<StandardError> serviceError(ServiceError e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Service error");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    /*
    * erro geral evitar vazamento de informações
    * */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> generalError(Exception e, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Internal server error");
        error.setMessage("An unexpected error occurred. Please try again later.");
        error.setPath(request.getRequestURI());
        logger.error("Erro interno: ", e);
        return ResponseEntity.status(status).body(error);
    }
}