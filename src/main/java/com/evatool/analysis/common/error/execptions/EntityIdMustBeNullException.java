package com.evatool.analysis.common.error.execptions;

public class EntityIdMustBeNullException extends RuntimeException{
    public EntityIdMustBeNullException(String entity) {
        super(String.format("A newly created '%s' cannot have an id.", entity));
    }
}
