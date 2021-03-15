package com.evatool.variants.common.error.exceptions;

public class IllegalAnalysisException extends IllegalArgumentException{
    public IllegalAnalysisException(String id){
        super(String.format("Analysis with id: '%s' cannot be found.", id));
    }
}
