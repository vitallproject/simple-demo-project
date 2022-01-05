package com.vital.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
