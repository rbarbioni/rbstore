package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.exception.BusinessException;
import br.com.rbarbioni.rbstore.model.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by renan on 11/02/17.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    public ResponseError handleException(BusinessException e, HttpServletResponse response, HttpServletRequest request){
        return processResponseError(response, request, e, HttpStatus.valueOf(e.getStatus()), e.getMessage());
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseError handleException(HttpClientErrorException e, HttpServletResponse response, HttpServletRequest request){
        return processResponseError(response, request, e, e.getStatusCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseError handleException(Exception e, HttpServletResponse response, HttpServletRequest request){
        logger.error("Error", e);

        ResponseError responseError = processResponseError(response, request, e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        if(e instanceof HttpMessageConversionException){
            responseError = processResponseError(response, request, e, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }

        response.setStatus(response.getStatus());
        return responseError;
    }


    private  ResponseError processResponseError (HttpServletResponse response, HttpServletRequest request, Exception e,  HttpStatus httpStatus, String message){
        response.setStatus(httpStatus.value());
        return new ResponseError(response.getStatus(), e.getClass().getName(), httpStatus.name(), message, request.getRequestURI());
    }
}