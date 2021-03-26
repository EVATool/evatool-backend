package com.evatool.variants.common.error.exceptions;

public class VariantsEntityNotFoundException extends IllegalArgumentException{

    public VariantsEntityNotFoundException(String id){
        super(String.format("Variant with id: '%s' cannot be found.", id));
    }

}
