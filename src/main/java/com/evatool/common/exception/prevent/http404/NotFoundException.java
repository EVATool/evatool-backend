package com.evatool.common.exception.prevent.http404;

import com.evatool.common.exception.prevent.PreventException;

public class NotFoundException extends PreventException {
    public NotFoundException(String message) {
        super(message);
    }
}
