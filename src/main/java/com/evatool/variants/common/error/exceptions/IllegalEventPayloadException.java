package com.evatool.variants.common.error.exceptions;

public class IllegalEventPayloadException extends IllegalArgumentException{
    public IllegalEventPayloadException(String payload){
        super(String.format("Event with payload: '%s' cannot be proceeded.", payload));
    }
}
