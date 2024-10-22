package com.MedGuard.utils.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                badRequest,
                LocalDateTime.now());
        return  ResponseEntity.status(apiExceptionResponse.getStatus()).body(apiExceptionResponse);
    }

    @ExceptionHandler(value = {ObjectNotValidException.class})
    public ResponseEntity<ApiExceptionResponse> handleObjectNotValidException(ObjectNotValidException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getErrors(),
                badRequest,
                LocalDateTime.now());
        return  ResponseEntity.status(apiExceptionResponse.getStatus()).body(apiExceptionResponse);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})

    public ResponseEntity<ApiExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e){
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return  ResponseEntity.status(apiExceptionResponse.getStatus()).body(apiExceptionResponse);
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    public ResponseEntity<ApiExceptionResponse> handleAuthorizationServiceException(AuthorizationServiceException e){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                unauthorized,
                LocalDateTime.now()
        );
        return  ResponseEntity.status(apiExceptionResponse.getStatus()).body(apiExceptionResponse);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ApiExceptionResponse> handleAuthenticationException(AuthenticationException e){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                e.getMessage(),
                unauthorized,
                LocalDateTime.now()
        );
        return  ResponseEntity.status(apiExceptionResponse.getStatus()).body(apiExceptionResponse);
    }


}
