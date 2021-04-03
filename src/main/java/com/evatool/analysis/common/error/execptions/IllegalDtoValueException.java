package com.evatool.analysis.common.error.execptions;


public class IllegalDtoValueException extends IllegalArgumentException{

    public IllegalDtoValueException(String reason) {
        super(reason);
    }

}
