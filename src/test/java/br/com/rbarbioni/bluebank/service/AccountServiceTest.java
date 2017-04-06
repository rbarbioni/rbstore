package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
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

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    private Account accountExist;
    private Account accountNew;


    @Before
    public void init(){
        accountExist = new Account("31449881114", "1000", "10000", "123123");
        accountNew   = new Account("31449881114", "9000", "90000", "123123");
    }


    @Test
    @Rollback
    public void save (){
        Account save = this.accountService.save(accountNew);
        Assert.assertNotNull(save);
    }

    @Test
    @Rollback
    public void find () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        Assert.assertNotNull(account);
    }

    @Test(expected = BlueBankException.class)
    @Rollback
    public void findInvalidAccount () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), "9999", "99999");
        Assert.assertNotNull(account);
    }

    @Test
    @Rollback
    public void depositar () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        BigDecimal saldoAtual = account.getSaldo();
        Double valorDeposito = Double.valueOf(200);
        account = this.accountService.depositar(account, valorDeposito);
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getSaldo(), saldoAtual.add(BigDecimal.valueOf(valorDeposito)));
    }

    @Test
    @Rollback
    public void sacar () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        Double valorSaque = Double.valueOf(200);
        BigDecimal saldoAtual = account.getSaldo();
        account = this.accountService.sacar(account, valorSaque);
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getSaldo(), saldoAtual.subtract(BigDecimal.valueOf(valorSaque)));
    }

    @Test(expected = BlueBankException.class)
    @Rollback
    public void sacarSaldoInsuficiente () throws JsonProcessingException {
        Account account = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        Double valorSaque = Double.valueOf(50000);
        this.accountService.sacar(account, valorSaque);
    }

    @Test
    @Rollback
    public void transferencia () throws JsonProcessingException {
        Account source = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        Account destination = this.accountService.findUnique("14140472669", "5000", "50000");

        BigDecimal valorTransferencia = BigDecimal.valueOf(Double.valueOf(200));
        BigDecimal sourceSaldoAtual = source.getSaldo();
        BigDecimal destinationSaldoAtual = destination.getSaldo();

        AccountTransferDto accountTransferDto = new AccountTransferDto(source, destination, valorTransferencia.doubleValue());
        source = this.accountService.transfer(accountTransferDto);
        Assert.assertNotNull(source);

        // Verifica se o valor saiu da conta origem
        Assert.assertEquals(source.getSaldo(), sourceSaldoAtual.subtract(valorTransferencia));

        destination = this.accountService.findUnique("14140472669", "5000", "50000");

        // Verifica de o valor entrou na conta destino
        Assert.assertEquals(destination.getSaldo(), destinationSaldoAtual.add(valorTransferencia));
    }

    @Test(expected = BlueBankException.class)
    @Rollback
    public void transferenciaContasOrigenEDestinoIguais () throws JsonProcessingException {
        Account source = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());
        Account destination = this.accountService.findUnique(this.accountExist.getCpf(), this.accountExist.getAgencia(), this.accountExist.getNumero());

        BigDecimal valorTransferencia = BigDecimal.valueOf(Double.valueOf(200));
        AccountTransferDto accountTransferDto = new AccountTransferDto(source, destination, valorTransferencia.doubleValue());
        this.accountService.transfer(accountTransferDto);
    }
}
