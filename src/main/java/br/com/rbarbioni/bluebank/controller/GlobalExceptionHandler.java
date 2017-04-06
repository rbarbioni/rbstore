package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.dto.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

/**
 * Created by renan on 11/02/17.
 */
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BlueBankException.class)
    public ResponseErrorDto handleBaseException(BlueBankException e){
        return new ResponseErrorDto(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseErrorDto handleException(Exception e){
        e.printStackTrace();
        if(e instanceof ServletException){
            return new ResponseErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }else{
            return new ResponseErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}