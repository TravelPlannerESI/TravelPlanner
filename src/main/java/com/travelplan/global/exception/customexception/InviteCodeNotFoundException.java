package com.travelplan.global.exception.customexception;

public class InviteCodeNotFoundException extends RuntimeException{
    public InviteCodeNotFoundException(String message) {
        super(message);
    }
    public InviteCodeNotFoundException() {
    }
}
