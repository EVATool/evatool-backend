package com.evatool.analysis.common.error.execptions;

public class EntityIdRequiredException extends RuntimeException   {

    public EntityIdRequiredException(String entity) {
        super(String.format("The '%s' must have an id for this operation.", entity));
    }
}
