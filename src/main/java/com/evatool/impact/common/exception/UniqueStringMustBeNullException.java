package com.evatool.impact.common.exception;

public class UniqueStringMustBeNullException extends RuntimeException {
    public UniqueStringMustBeNullException(String entity) {
        super(String.format("A newly created '%s' cannot have an uniqueString.", entity));
    }
}
