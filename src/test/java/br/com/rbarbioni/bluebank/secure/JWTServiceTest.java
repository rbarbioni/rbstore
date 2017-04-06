package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    Account account;

    @Before
    public void init(){
        account = new Account("31449881114", "1500", "50010", "123123");
    }

    @Test
    public void encodeTest() throws JsonProcessingException {
        String token = this.jwtService.encode(account);
        Assert.assertNotNull(token);
    }

    @Test
    public void decodeTest() throws IOException {
        String token = this.jwtService.encode(account);
        Account account = this.jwtService.decode(token);
        Assert.assertNotNull(account);
    }
}
