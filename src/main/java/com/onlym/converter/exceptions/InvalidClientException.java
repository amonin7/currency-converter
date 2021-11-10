package com.onlym.converter.exceptions;

public class InvalidClientException extends RuntimeException {
    public InvalidClientException() {
        super("This client is invalid");
    }
}
