package com.evatool.common.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(Exception e) {
        super(e);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
