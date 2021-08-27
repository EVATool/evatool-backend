package com.evatool.common.exception.handle;

import com.evatool.common.exception.*;
import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.http400.ImportJsonException;
import com.evatool.common.exception.functional.http401.UnauthorizedException;
import com.evatool.common.exception.functional.http403.CrossRealmAccessException;
import com.evatool.common.exception.functional.http404.EntityNotFoundException;
import com.evatool.common.exception.functional.http422.PropertyCannotBeNullException;
import com.evatool.common.exception.functional.http422.PropertyCannotBeUpdatedException;
import com.evatool.common.exception.functional.http422.PropertyIsInvalidException;
import com.evatool.common.exception.functional.http422.PropertyMustBeNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception exception,
                                                                     WebRequest webRequest,
                                                                     HttpStatus httpStatus) {
    logger.warn(
            "{} handled. Returning HttpStatus {}. Message: {}",
            exception.getClass().getSimpleName(),
            httpStatus,
            exception.getMessage());

    var errorMessage = new ErrorMessage(
            exception,
            ((ServletWebRequest) webRequest).getRequest().getRequestURI(),
            httpStatus);

    return new ResponseEntity<>(errorMessage, httpStatus);
  }

  private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(FunctionalException exception,
                                                                     WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest, exception.getHttpStatus());
  }

  // ##############################
  // Functional Exceptions.
  // ##############################

  @ExceptionHandler(FunctionalException.class)
  public ResponseEntity<ErrorMessage> handle(FunctionalException exception, WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest);
  }

  //@ExceptionHandler(EntityStillReferencedException.class)
  //public ResponseEntity<ErrorMessage> handle(EntityStillReferencedException exception, WebRequest webRequest) {
  //  return getErrorMessageResponseEntity(exception, webRequest);
  //}

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

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorMessage> handle(UnauthorizedException exception, WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ErrorMessage> handle(InternalServerErrorException exception, WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ImportJsonException.class)
  public ResponseEntity<ErrorMessage> handle(ImportJsonException exception, WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.BAD_REQUEST);
  }

  // ##############################
  // Non-Functional Exceptions.
  // ##############################

//  @ExceptionHandler(ValidationException.class)
//  public ResponseEntity<ErrorMessage> handle(ValidationException exception, WebRequest webRequest) {
//    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.BAD_REQUEST);
//  }
//
//  @ExceptionHandler(ConstraintViolationException.class)
//  public ResponseEntity<ErrorMessage> handle(ConstraintViolationException exception, WebRequest webRequest) {
//    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.BAD_REQUEST);
//  }
}
