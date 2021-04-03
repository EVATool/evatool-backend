package com.evatool.variants.common.error.exceptions;

public class VariantStillReferredException extends IllegalArgumentException{
    public VariantStillReferredException(){
        super("Variant can not be archived or deleted, because a Requirement is still referring to it");
    }
}
