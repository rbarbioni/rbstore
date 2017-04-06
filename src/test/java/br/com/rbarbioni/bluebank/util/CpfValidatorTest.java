package br.com.rbarbioni.bluebank.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class CpfValidatorTest {

    CpfValidator cpfValidator;
    @Before
    public void init(){
        cpfValidator = new CpfValidator();
        cpfValidator.initialize(null);
    }

    @Test
    public void cpfValidTest(){
        Assert.assertTrue(cpfValidator.isValid("31449881114", null));
    }

    @Test
    public void cpfInvalidTest(){
        Assert.assertFalse(cpfValidator.isValid("12312312322", null));
    }

    @Test
    public void cpfValidWithMaskTest(){
        Assert.assertTrue(cpfValidator.isValid("314.498.811-14", null));
    }

    @Test
    public void cpfInalidWithMaskTest(){
        Assert.assertFalse(cpfValidator.isValid("314.498.811-11", null));
    }

    @Test
    public void cpfInalidNullTest(){
        Assert.assertFalse(cpfValidator.isValid(null, null));
    }

    @Test
    public void cpfNotNumberTest(){
        Assert.assertFalse(cpfValidator.isValid("No_Number", null));
    }
}
