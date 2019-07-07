package com.letswork.springservice.generalexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
