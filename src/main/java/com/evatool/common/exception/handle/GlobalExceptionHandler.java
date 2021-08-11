package com.evatool.common.exception.handle;

import com.evatool.common.exception.*;
import com.evatool.common.exception.functional.EntityStillReferencedException;
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

    @ExceptionHandler(EntityStillReferencedException.class)
    public ResponseEntity<ErrorMessage> handle(EntityStillReferencedException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyCannotBeNullException.class)
    public ResponseEntity<ErrorMessage> handle(PropertyCannotBeNullException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(PropertyCannotBeUpdatedException.class)
    public ResponseEntity<ErrorMessage> handle(PropertyCannotBeUpdatedException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(PropertyIsInvalidException.class)
    public ResponseEntity<ErrorMessage> handle(PropertyIsInvalidException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(PropertyMustBeNullException.class)
    public ResponseEntity<ErrorMessage> handle(PropertyMustBeNullException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CrossRealmAccessException.class)
    public ResponseEntity<ErrorMessage> handle(CrossRealmAccessException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ImportJsonException.class)
    public ResponseEntity<ErrorMessage> handle(ImportJsonException exception, WebRequest webRequest) {
        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.BAD_REQUEST);
    }

    // TODO All exceptions that are thrown by spring for validation (e.g. MethodArgumentNotValidException, MethodArgumentTypeMismatchException)
    //  should be caught here in order to return with ErrorMessage and 400. If thats the case, then 500 can also return
    //  return ErrorMessage, because it wont catch exception that are used for spring validation.
    //  https://stackoverflow.com/questions/33663801/how-do-i-customize-default-error-message-from-spring-valid-validation/33665121

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorMessage> handle(Exception exception, WebRequest webRequest) {
//        return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception exception, WebRequest webRequest, HttpStatus httpStatus) {
        logger.warn("{} handled. Returning HttpStatus {}. Message: {}", exception.getClass().getSimpleName(), httpStatus, exception.getMessage());
        var errorMessage = new ErrorMessage(exception, ((ServletWebRequest) webRequest).getRequest().getRequestURI(), httpStatus);
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}