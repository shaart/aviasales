package com.epam.aviasales.exceptions;

public class IncorrectPasswordException extends AccountValidateException {

  public IncorrectPasswordException(String message) {
    super(message);
  }
}
