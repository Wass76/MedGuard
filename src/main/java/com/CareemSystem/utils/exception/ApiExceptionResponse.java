package com.CareemSystem.utils.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ApiExceptionResponse {

    private final Object message;
    private final HttpStatus status;
    private final LocalDateTime localDateTime;
}
