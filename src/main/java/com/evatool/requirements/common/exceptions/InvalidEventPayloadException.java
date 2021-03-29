package com.evatool.requirements.common.exceptions;

public class InvalidEventPayloadException extends RuntimeException {
    public InvalidEventPayloadException(String json, Exception cause) {
        super(String.format("Invalid payload received: %s", json), cause);
    }
}
