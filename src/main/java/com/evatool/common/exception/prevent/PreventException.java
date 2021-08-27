package com.evatool.common.exception.prevent;

// A PreventException should be prevented to happen by the developer making calls to the backend.
public abstract class PreventException extends RuntimeException {
    public PreventException(String message) {
        super(message);
    }
}
