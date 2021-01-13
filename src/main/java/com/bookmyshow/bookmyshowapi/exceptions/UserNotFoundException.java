package com.bookmyshow.bookmyshowapi.exceptions;

public class UserNotFoundException extends BookMyShowException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
