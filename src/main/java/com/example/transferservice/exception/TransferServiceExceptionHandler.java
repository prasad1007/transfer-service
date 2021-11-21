package com.example.transferservice.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransferServiceExceptionHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(TransferServiceExceptionHandler.class);

  @ExceptionHandler({AccountNotExistException.class})
  @ResponseStatus(NOT_FOUND)
  public void handleAccountNotExistException(final AccountNotExistException e) {
    LOGGER.error("AccountNotExistException happened : {}", e.getMessage());
  }

  @ExceptionHandler({NoSufficientBalanceException.class})
  @ResponseStatus(BAD_REQUEST)
  public void handleNoSufficientBalanceException(final NoSufficientBalanceException e) {
    LOGGER.error("NoSufficientBalanceException happened : {}", e.getMessage());
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public void handleOtherException(final Exception e) {
    LOGGER.error("Exception happened : {}", e.getMessage());
  }
}
