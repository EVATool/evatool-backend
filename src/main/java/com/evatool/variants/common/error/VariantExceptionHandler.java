package com.evatool.variants.common.error;

import com.evatool.variants.common.error.exceptions.IllegalAnalysisException;
import com.evatool.variants.common.error.exceptions.VariantStillReferredException;
import com.evatool.variants.common.error.exceptions.VariantsEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class VariantExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(VariantExceptionHandler.class);

    @ExceptionHandler(IllegalAnalysisException.class)
    public ResponseEntity<VariantsErrorMessage> handleEntityNotFoundException(IllegalAnalysisException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VariantsEntityNotFoundException.class)
    public ResponseEntity<VariantsErrorMessage> handleEntityNotFoundException(VariantsEntityNotFoundException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VariantStillReferredException.class)
    public ResponseEntity<VariantsErrorMessage> handleEntityNotFoundException(VariantStillReferredException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }



    private ResponseEntity<VariantsErrorMessage> createErrorResponseEntity(Exception exception, WebRequest webRequest, HttpStatus httpStatus) {
        logger.warn("{} handled. Returning HttpStatus {}. Message: {}", exception.getClass().getSimpleName(), httpStatus, exception.getMessage());
        var errorMessage = new VariantsErrorMessage(exception, ((ServletWebRequest) webRequest).getRequest().getRequestURI(), httpStatus);
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

}