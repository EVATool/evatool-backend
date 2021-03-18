package com.evatool.impact.common.exception;

public class NumericIdCannotBeUpdatedException extends RuntimeException {
    public NumericIdCannotBeUpdatedException(String entity) {
        super(String.format("Cannot update entity '%s' numericIds are not matching.", entity));
    }
}
