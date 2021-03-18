package com.evatool.impact.common.exception;

public class NumericIdMustBeNullException extends RuntimeException {
    public NumericIdMustBeNullException(String entity) {
        super(String.format("A newly created '%s' cannot have an numericId.", entity));
    }
}
