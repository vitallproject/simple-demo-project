package com.vital.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.vital.validator.Error;
import java.util.List;
@AllArgsConstructor
public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;
}
