package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    private LoginDto loginDto;

    @Test
    @Rollback
    public void login () throws JsonProcessingException {
        LoginDto loginDto = new LoginDto("14140472669", "1234", "12345", "5f4dcc3b5aa765d61d8327deb882cf99");
        Account login = this.loginService.login(loginDto);
        Assert.assertNotNull(login);
        Assert.assertNotNull(login.getToken());
    }

    @Test(expected = BlueBankException.class)
    @Rollback
    public void loginInvalido () throws JsonProcessingException {
        LoginDto loginDto = new LoginDto("31449881114", "2222", "22222", "xxxxxxxx");
        Account login = this.loginService.login(loginDto);
        Assert.assertNotNull(login);
        Assert.assertNotNull(login.getToken());
    }

    @Test(expected = BlueBankException.class)
    @Rollback
    public void loginSenhaInvalido () throws JsonProcessingException {
        LoginDto loginDto = new LoginDto("14140472669", "1234", "12345", "5f4dcc3b5aa765d61d8327deb882cf9XXX");
        Account login = this.loginService.login(loginDto);
        Assert.assertNotNull(login);
        Assert.assertNotNull(login.getToken());
    }
}
