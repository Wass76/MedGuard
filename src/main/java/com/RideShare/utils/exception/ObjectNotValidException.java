package com.RideShare.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ObjectNotValidException extends RuntimeException {
    private final Set<String> errors;
}
