package com.evatool.common.exception.handle;

import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.functional.FunctionalException;
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

  // ##############################
  // Non-Functional Exceptions.
  // ##############################

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ErrorMessage> handle(InternalServerErrorException exception, WebRequest webRequest) {
    return getErrorMessageResponseEntity(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
