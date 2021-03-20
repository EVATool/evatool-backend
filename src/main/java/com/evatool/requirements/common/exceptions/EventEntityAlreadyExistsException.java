package com.evatool.requirements.common.exceptions;

public class EventEntityAlreadyExistsException extends RuntimeException {
    public EventEntityAlreadyExistsException() {
        super("The entity transmitted in the event already exist.");
    }
}
