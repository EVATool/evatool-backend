package com.evatool.common.exception;

import org.json.JSONException;

public class ImportJsonException extends RuntimeException{
    public ImportJsonException(JSONException jsonException) {
        super(jsonException.getMessage());
    }

    public ImportJsonException(String message) {
        super(message);
    }
}
