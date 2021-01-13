package com.bookmyshow.bookmyshowapi.exceptions;

public class UserAlreadyPresentException extends BookMyShowException{
    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
