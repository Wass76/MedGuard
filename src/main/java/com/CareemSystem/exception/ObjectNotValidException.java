package com.CareemSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ObjectNotValidException extends RuntimeException {
    private List<String> errors;
}
