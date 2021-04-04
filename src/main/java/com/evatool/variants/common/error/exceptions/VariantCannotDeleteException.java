package com.evatool.variants.common.error.exceptions;

public class VariantCannotDeleteException extends IllegalArgumentException{
    public VariantCannotDeleteException(String id){
        super(String.format("Variant ID: %s can not be deleted, because the variant must be archived.", id));
    }
}
