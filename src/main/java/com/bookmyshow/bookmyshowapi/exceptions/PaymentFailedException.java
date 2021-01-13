package com.bookmyshow.bookmyshowapi.exceptions;

public class PaymentFailedException extends BookMyShowException{
    public PaymentFailedException(String message) {
        super(message);
    }
}
