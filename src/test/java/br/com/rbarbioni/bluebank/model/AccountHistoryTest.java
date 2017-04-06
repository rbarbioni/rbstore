package br.com.rbarbioni.bluebank.model;

import br.com.rbarbioni.bluebank.model.enums.Operation;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by renan on 13/02/17.
 */
public class AccountHistoryTest {

    @Test
    public void gettersAndSetterTest(){

        Account account1 = new Account("31449881114", "1500", "50010", "123123");

        AccountHistory account = new AccountHistory(account1, Operation.TRANSFERENCIA_SAIDA, BigDecimal.TEN);
        Assert.assertNotNull(account.getId());

        Assert.assertNull(account.getModifieldDate());
        Assert.assertNotNull(account.getCpf());
        Assert.assertNotNull(account.getAgencia());
        Assert.assertNotNull(account.getNumero());

        Assert.assertNotNull(account.getId());
        Assert.assertNull(account.getModifieldDate());
        Assert.assertNotNull(account.getCpf());
        Assert.assertNotNull(account.getAgencia());
        Assert.assertNotNull(account.getNumero());
        Assert.assertNotNull(account.getOperation());
        Assert.assertNotNull(account.getValor());
        Assert.assertNotNull(account.getCreatedDate());

        Assert.assertEquals(account.getOperation(), Operation.TRANSFERENCIA_SAIDA);
        Assert.assertEquals(account.getValor(), BigDecimal.TEN);
    }

    @Test
    public void constructorDefaultTest(){
        AccountHistory accountHistory = new AccountHistory();
        Assert.assertNotNull(accountHistory);
    }
}
