package com.MedGuard.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseClass {
    private   String message;
    private   HttpStatus status;
    private   LocalDateTime localDateTime;
    private   Object body;


    public ApiResponseClass(String message, HttpStatus status, LocalDateTime localDateTime) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
    }

    public ApiResponseClass(String message, HttpStatus status, LocalDateTime localDateTime, Object body) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
        this.body = body;
    }

}
