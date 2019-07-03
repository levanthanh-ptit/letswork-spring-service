package com.letswork.springservice.genneralexception;

public class NoContentException extends  RuntimeException {
    private String message;


    public NoContentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
