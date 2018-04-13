package com.epam.aviasales.exceptions;

public class AccountValidateException extends Exception {
    protected String message;
    public AccountValidateException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
