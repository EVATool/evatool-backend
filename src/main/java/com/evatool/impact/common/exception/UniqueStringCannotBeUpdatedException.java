package com.evatool.impact.common.exception;

public class UniqueStringCannotBeUpdatedException extends RuntimeException {
    public UniqueStringCannotBeUpdatedException(String entity) {
        super(String.format("Cannot update entity '%s' uniqueStrings are not matching.", entity));
    }
}
