package br.com.rbarbioni.bluebank.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class BlueBankExceptionTest {

    BlueBankException blueBankException;

    @Before
    public void init (){
        blueBankException = new BlueBankException(200, "message");
    }

    @Test
    public void gettersAndSetterTest(){
        Assert.assertNotNull(blueBankException);
        Assert.assertNotNull(blueBankException.getMessage());
        Assert.assertNotNull(blueBankException.getStatus());
    }
}
