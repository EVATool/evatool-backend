package com.evatool.impact.common.exception.handle;

import com.evatool.impact.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityIdRequiredException.class)
    public ResponseEntity<ErrorMessage> handleEntityIdRequiredException(EntityIdRequiredException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityIdMustBeNullException.class)
    public ResponseEntity<ErrorMessage> handleEntityIdMustBeNullException(EntityIdMustBeNullException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UniqueStringMustBeNullException.class)
    public ResponseEntity<ErrorMessage> handleNumericIdMustBeNullException(UniqueStringMustBeNullException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UniqueStringCannotBeUpdatedException.class)
    public ResponseEntity<ErrorMessage> handleNumericIdCannotBeUpdatedException(UniqueStringCannotBeUpdatedException exception, WebRequest webRequest) {
        return createErrorResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorMessage> createErrorResponseEntity(Exception exception, WebRequest webRequest, HttpStatus httpStatus) {
        logger.warn("{} handled. Returning HttpStatus {}. Message: {}", exception.getClass().getSimpleName(), httpStatus, exception.getMessage());
        var errorMessage = new ErrorMessage(exception, ((ServletWebRequest) webRequest).getRequest().getRequestURI(), httpStatus);
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}
