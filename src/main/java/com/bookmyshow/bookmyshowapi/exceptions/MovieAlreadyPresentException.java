package com.bookmyshow.bookmyshowapi.exceptions;

public class MovieAlreadyPresentException extends BookMyShowException{
    public MovieAlreadyPresentException(String message) {
        super(message);
    }
}
