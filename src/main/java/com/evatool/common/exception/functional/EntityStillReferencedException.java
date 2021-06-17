package com.evatool.common.exception.functional;

public class EntityStillReferencedException extends FunctionalException {
    public EntityStillReferencedException(String message, int functionalErrorCode) {
        super(message, functionalErrorCode);
    }
}
