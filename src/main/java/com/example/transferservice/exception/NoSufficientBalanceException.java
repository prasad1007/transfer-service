package com.example.transferservice.exception;

public class NoSufficientBalanceException extends Exception {

  public NoSufficientBalanceException(final String message) {
    super(message);
  }
}
