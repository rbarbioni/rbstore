package br.com.rbarbioni.bluebank.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by renan on 13/02/17.
 */
public class AccountTest {

    @Test
    public void gettersAndSetterTest(){

        Account account = new Account("31449881114", "1500", "50010", "123123");
        account.setToken("123123");
        Assert.assertNotNull(account.getId());
        Assert.assertNull(account.getAuthorities());
        Assert.assertNull(account.getModifieldDate());
        Assert.assertTrue(account.isAccountNonExpired());
        Assert.assertTrue(account.isAccountNonLocked());
        Assert.assertTrue(account.isCredentialsNonExpired());
        Assert.assertTrue(account.isEnabled());
        Assert.assertNotNull(account.getToken());
        Assert.assertNotNull(account.getPassword());
        Assert.assertNotNull(account.getSaldo());
        Assert.assertNotNull(account.getCpf());
        Assert.assertNotNull(account.getAgencia());
        Assert.assertNotNull(account.getNumero());
        Assert.assertNotNull(account.getUsername());
    }

    @Test
    public void sacarAndDepositarTest(){
        Account account = new Account();
        account.depositar(BigDecimal.valueOf(1000));
        Assert.assertEquals(account.getSaldo(), BigDecimal.valueOf(1000));

        account.sacar(BigDecimal.valueOf(1000));
        Assert.assertEquals(account.getSaldo(), BigDecimal.ZERO);
    }

    @Test
    public void equalsTest(){

        Account account = new Account("31449881114", "1500", "50010", "123123");
        Assert.assertFalse(account.equals(new Account()));
    }

    @Test
    public void notEqualsNullTest(){

        Account account = new Account("31449881114", "1500", "50010", "123123");
        Assert.assertFalse(account.equals(null));
    }

    @Test
    public void notEqualsOtherObjectTest(){

        Account account = new Account("31449881114", "1500", "50010", "123123");
        Assert.assertFalse(account.equals(new String()));
    }
}
