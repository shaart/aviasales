package com.epam.aviasales.exceptions;

public class IncorrectLoginException extends AccountValidateException {
    public IncorrectLoginException(String message){
        super(message);
    }
}
