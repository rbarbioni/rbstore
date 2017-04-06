package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by renan on 13/02/17.
 */
public class AccountTransferDtoTest {

    AccountTransferDto accountTransferDto;

    @Before
    public void init (){
        Account source = new Account("31449881114", "1500", "50010", "123123");
        Account destination = new Account("65261248876", "1600", "60010", "123123");
        accountTransferDto = new AccountTransferDto(source, destination, 10.0);
    }

    @Test
    public void gettersAndSetterTest(){
        Assert.assertNotNull(accountTransferDto);
        Assert.assertNotNull(accountTransferDto.getSource());
        Assert.assertNotNull(accountTransferDto.getDestination());
        Assert.assertEquals(accountTransferDto.getAmount(), Double.valueOf(10));
    }

    @Test
    public void sourceAndDestinationNotEqualsTest(){
        Assert.assertNotEquals(accountTransferDto.getSource(), accountTransferDto.getDestination());
    }
}
