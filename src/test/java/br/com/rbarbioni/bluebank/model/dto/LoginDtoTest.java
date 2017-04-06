package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.util.CpfValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class LoginDtoTest {

    LoginDto loginDto;

    @Before
    public void init (){
        loginDto = new LoginDto("31449881114", "1500", "50010", "123123");
    }

    @Test
    public void gettersAndSetterTest(){
        Assert.assertNotNull(loginDto);
        Assert.assertNotNull(loginDto.getCpf());
        Assert.assertNotNull(loginDto.getPassword());
        Assert.assertNotNull(loginDto.getAgencia());
        Assert.assertNotNull(loginDto.getNumero());
    }

    @Test
    public void cpfValidTest(){
        Assert.assertTrue(new CpfValidator().isValid(loginDto.getCpf(), null));
    }
}
