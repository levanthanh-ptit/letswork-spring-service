package com.letswork.springservice.generalexception.model;

public class ExceptionModel {

    private String message;

    public ExceptionModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
