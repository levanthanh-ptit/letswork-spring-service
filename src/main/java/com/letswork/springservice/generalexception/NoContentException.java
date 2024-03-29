package com.letswork.springservice.generalexception;

public class NoContentException extends RuntimeException {
    private String message;

    public NoContentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
