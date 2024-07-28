package com.CareemSystem.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


}
