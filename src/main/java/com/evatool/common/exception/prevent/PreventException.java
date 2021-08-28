package com.evatool.common.exception.prevent;

import com.evatool.common.exception.HttpStatusException;
import org.springframework.http.HttpStatus;

// A PreventException should be prevented to happen by the developer making calls to the backend.
public abstract class PreventException extends HttpStatusException {
    protected PreventException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
