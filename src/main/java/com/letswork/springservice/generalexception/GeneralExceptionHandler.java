package com.letswork.springservice.generalexception;

import com.letswork.springservice.generalexception.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ExceptionModel handleBadRequest(BadRequestException ex){
        System.out.println("ControllerAdvice");
        return new ExceptionModel(ex.getMessage());
    }

    @ExceptionHandler(value = {NoContentException.class})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    ExceptionModel handleNoContent(NoContentException ex){
        return new ExceptionModel(ex.getMessage());
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    ExceptionModel handleAuthentication(AuthenticationException ex) {
        return new ExceptionModel(ex.getMessage());
    }
}
