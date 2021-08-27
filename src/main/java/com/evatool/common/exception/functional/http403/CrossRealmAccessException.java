package com.evatool.common.exception.functional.http403;

public class CrossRealmAccessException extends RuntimeException {
    public CrossRealmAccessException() {
        super("This entity does belong to a different realm");
    }
}
