package com.evatool.common.exception;

public class CrossRealmAccessException extends RuntimeException {
    public CrossRealmAccessException() {
        super("This entity does belong to a different realm");
    }
}
