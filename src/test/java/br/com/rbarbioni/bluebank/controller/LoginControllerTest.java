package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.LoginDto;
import br.com.rbarbioni.bluebank.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

/**
 * Created by renan on 13/02/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @MockBean
    private LoginService loginService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Account account;

    private LoginDto loginDto;

    @Before
    public void init (){
        account = new Account("31449881114", "1000", "10000", "5f4dcc3b5aa765d61d8327deb882cf99");
        loginDto = new LoginDto("31449881114", "1000", "10000", "5f4dcc3b5aa765d61d8327deb882cf99");
    }

    @Test
    public void login () throws JsonProcessingException {
        given(loginService.login(any(LoginDto.class))).willReturn(account);

        Account account = this.restTemplate.postForObject("/api/login", loginDto, Account.class);
        Assert.assertNotNull(account);
    }

}
