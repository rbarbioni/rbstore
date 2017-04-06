package br.com.rbarbioni.bluebank.model.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class ResponseErrorTest {

    ResponseErrorDto responseErrorDto;

    @Before
    public void init (){
        responseErrorDto = new ResponseErrorDto("message", 200);
    }

    @Test
    public void gettersAndSetterTest(){
        Assert.assertNotNull(responseErrorDto);
        Assert.assertNotNull(responseErrorDto.getMessage());
        Assert.assertNotNull(responseErrorDto.getStatus());
        Assert.assertEquals(responseErrorDto.getStatus(), Integer.valueOf(200));
    }
}
