package com.evatool.analysis.common.error;

import com.evatool.analysis.common.error.execptions.EntityIdMustBeNullException;
import com.evatool.analysis.common.error.execptions.EntityIdRequiredException;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.common.error.execptions.IllegalDtoValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AnalysisExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisExceptionHandler.class);
    private static final String errorMessage422 = "{} handled. Returning HttpStatus UNPROCESSABLE_ENTITY (422)";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AnalysisErrorMessage> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest webRequest) {
        logger.info("{} handled. Returning HttpStatus NOT_FOUND (404)", exception.getClass().getSimpleName());
        AnalysisErrorMessage errorMessage = new AnalysisErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalDtoValueException.class)
    public ResponseEntity<AnalysisErrorMessage> handleIllegalDtoValueException(IllegalDtoValueException exception, WebRequest webRequest) {
        logger.info(errorMessage422, exception.getClass().getSimpleName());
        AnalysisErrorMessage errorMessage = new AnalysisErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityIdMustBeNullException.class)
    public ResponseEntity<AnalysisErrorMessage> handleEntityIdMustBeNullException(EntityIdMustBeNullException exception, WebRequest webRequest) {
        logger.info(errorMessage422, exception.getClass().getSimpleName());
        AnalysisErrorMessage errorMessage = new AnalysisErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityIdRequiredException.class)
    public ResponseEntity<AnalysisErrorMessage> handleEntityIdRequiredException(EntityIdRequiredException exception, WebRequest webRequest) {
        logger.info(errorMessage422, exception.getClass().getSimpleName());
        AnalysisErrorMessage errorMessage = new AnalysisErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getUri(WebRequest webRequest) {
        return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
    }
}
