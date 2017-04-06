package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import br.com.rbarbioni.bluebank.model.enums.Operation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountHistoryServiceTest {

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private AccountService accountService;


    private Account accountExist;
    private Account accountNew;


    @Before
    public void init(){
        accountExist = new Account("93041807084", "4000", "40000", "123123");
        accountNew   = new Account("31449881114", "7777", "77777", "123123");
    }

    @Test
    @Rollback
    public void save () throws JsonProcessingException {

        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        AccountHistory accountHistory = this.accountHistoryService.save(account, Operation.valueOf("TRANSFERENCIA_SAIDA"), BigDecimal.valueOf(200));
        Assert.assertNotNull(accountHistory);
    }

    @Test
    @Rollback
    public void list () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        AccountHistory accountHistory = this.accountHistoryService.save(account, Operation.TRANSFERENCIA_SAIDA, BigDecimal.valueOf(200));
        List<AccountHistory> list = this.accountHistoryService.find(accountHistory.getCpf(), accountHistory.getAgencia(), accountHistory.getNumero());
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }
}
