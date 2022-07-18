package com.travelplan.global.exception.customexception;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException() {
    }

    public IdNotFoundException(String message) {
        super(message);
    }
}
