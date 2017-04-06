package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.dto.ResponseErrorDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;

/**
 * Created by renan on 13/02/17.
 */
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Before
    public void init (){
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void handleBaseExceptionTest (){
        ResponseErrorDto responseErrorDto = globalExceptionHandler.handleBaseException(new BlueBankException(HttpStatus.BAD_REQUEST.value(), ""));
        Assert.assertNotNull(responseErrorDto);
    }

    @Test
    public void handleExceptionTest (){
        ResponseErrorDto responseErrorDto = globalExceptionHandler.handleException(new SecurityException(""));
        Assert.assertNotNull(responseErrorDto);
    }

    @Test
    public void handleServletExceptionTest (){
        ResponseErrorDto responseErrorDto = globalExceptionHandler.handleException(new ServletException(""));
        Assert.assertNotNull(responseErrorDto);
    }
}
