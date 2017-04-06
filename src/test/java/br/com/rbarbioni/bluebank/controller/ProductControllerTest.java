package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.model.enums.Operation;
import br.com.rbarbioni.bluebank.secure.JWTService;
import br.com.rbarbioni.bluebank.service.AccountHistoryService;
import br.com.rbarbioni.bluebank.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

/**
 * Created by renan on 13/02/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountHistoryService accountHistoryService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JWTService jwtService;

    private Account account = null;

    @Before
    public void init () throws JsonProcessingException {
        account = new Account("31449881114", "1000", "10000");
    }

    @Test
    public void findAccount () throws JsonProcessingException {
        given(accountService.findUnique(anyString(), anyString(), anyString())).willReturn(account);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.encode(this.account));

        HttpEntity entity = new HttpEntity(headers);

        HttpEntity<Account> response = restTemplate.exchange(
                String.format("/api/account?cpf=%s&agencia=%s&numero=%s", this.account.getCpf(), this.account.getAgencia(), this.account.getNumero() ), HttpMethod.GET, entity, Account.class);

        Account account = response.getBody();
        Assert.assertNotNull(account);
    }

    @Test
    public void statement () throws JsonProcessingException {
        given(accountHistoryService.find(anyString(), anyString(), anyString())).willReturn(Arrays.asList(new AccountHistory(account, Operation.TRANSFERENCIA_SAIDA, BigDecimal.TEN)));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.encode(this.account));

        HttpEntity entity = new HttpEntity(headers);

        HttpEntity<String> response = restTemplate.exchange(
                String.format("/api/account/statement?cpf=%s&agencia=%s&numero=%s", this.account.getCpf(), this.account.getAgencia(), this.account.getNumero() ), HttpMethod.GET, entity, String.class);

        String body = response.getBody();
        Assert.assertNotNull(body);
    }

    @Test
    public void transfer () throws JsonProcessingException {
        given(accountService.transfer(any(AccountTransferDto.class))).willReturn(account);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.encode(this.account));

        AccountTransferDto accountTransferDto = new AccountTransferDto(account, new Account("31449881114", "2000", "20000"), Double.valueOf(100));
        HttpEntity entity = new HttpEntity(accountTransferDto, headers);

        HttpEntity<Account> response = restTemplate.exchange("/api/account/transfer", HttpMethod.POST, entity, Account.class);

        Account account = response.getBody();
        Assert.assertNotNull(account);
    }

}
