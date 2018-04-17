package com.epam.aviasales.exceptions;

public class LoginNotExistException extends AccountValidateException {

  public LoginNotExistException(String message) {
    super(message);
  }
}
