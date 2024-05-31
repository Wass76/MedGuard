package com.CareemSystem.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ApiException {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime localDateTime;
}
