package com.CareemSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class ObjectNotValidException extends RuntimeException {
    private final Set<String> errors;
}
