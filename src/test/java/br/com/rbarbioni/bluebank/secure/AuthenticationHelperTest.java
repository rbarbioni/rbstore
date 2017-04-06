package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationHelperTest {

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @Test
    public void authTest() throws JsonProcessingException {

        Account account = new Account("31449881114", "1500", "50010", "123123");
        Authentication authentication = authenticationHelper.auth(account);
        Assert.assertNotNull(authentication);
    }
}
