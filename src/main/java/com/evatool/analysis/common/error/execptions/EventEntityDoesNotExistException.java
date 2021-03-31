package com.evatool.analysis.common.error.execptions;

public class EventEntityDoesNotExistException extends RuntimeException {
    public EventEntityDoesNotExistException() {
        super("The entity transmitted in the event does not exist.");
    }
}
