package com.evatool.variants.common.error.exceptions;

public class VariantNotArchivedException extends IllegalArgumentException{
    public VariantNotArchivedException(){
        super(String.format("Variant can not be archived, because a Requirement is still referring to it"));
    }
}
