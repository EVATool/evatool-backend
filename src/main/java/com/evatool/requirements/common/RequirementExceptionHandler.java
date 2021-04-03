package com.evatool.requirements.common;


import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.common.exceptions.IllegalDtoValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RequirementExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequirementExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RequirementsErrorMessage> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest webRequest) {
        logger.error("{} handled. Returning HttpStatus NOT_FOUND (404)", exception.getClass().getSimpleName());
        RequirementsErrorMessage errorMessage = new RequirementsErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalDtoValueException.class)
    public ResponseEntity<RequirementsErrorMessage> handleIllegalDtoValueException(IllegalDtoValueException exception, WebRequest webRequest) {
        logger.error("{} handled. Returning HttpStatus UNPROCESSABLE_ENTITY (422)", exception.getClass().getSimpleName());
        RequirementsErrorMessage errorMessage = new RequirementsErrorMessage(exception, exception.getMessage(), getUri(webRequest), HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getUri(WebRequest webRequest) {
        return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
    }
}
