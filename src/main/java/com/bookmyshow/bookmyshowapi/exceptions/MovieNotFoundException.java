package com.bookmyshow.bookmyshowapi.exceptions;

public class MovieNotFoundException extends BookMyShowException{
    public MovieNotFoundException(String message) {
        super(message);
    }
}
