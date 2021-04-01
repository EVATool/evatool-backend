package com.evatool.analysis.common.error.execptions;

public class EventEntityAlreadyExistsException extends RuntimeException{

    public EventEntityAlreadyExistsException(){
        super("The entity transmitted in the event already exist.");
    }
}
