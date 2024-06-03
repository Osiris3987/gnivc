package com.example.portal_service.web.controller;


import com.example.portal_service.model.exception.AccessDeniedException;
import com.example.portal_service.model.exception.ErrorMessage;
import com.example.portal_service.model.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;


@ControllerAdvice
@ResponseBody
public class Handler {

    private final String VALIDATION_EXCEPTION = "Validation exception";
    private final String INVALID_INPUT_EXCEPTION = "Invalid input";
    private final String NOT_FOUND_EXCEPTION = "Not found";
    private final String ACCESS_DENIED_EXCEPTION = "Access denied";
    private final String UNAUTHORIZED_EXCEPTION = "Invalid password or email!";
    private final String SIGNATURE_JWT_EXCEPTION = "JWT signature does not match locally computed signature";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), NOT_FOUND_EXCEPTION, ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), new Date(), VALIDATION_EXCEPTION, ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {JsonParseException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class, MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> jsonParseException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), INVALID_INPUT_EXCEPTION, ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ACCESS_DENIED_EXCEPTION, ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), request.getDescription(false), ex.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}