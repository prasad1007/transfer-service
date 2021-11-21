package com.example.transferservice.exception;

public class AccountNotExistException extends Exception {

  public AccountNotExistException(final String message) {
    super(message);
  }
}
