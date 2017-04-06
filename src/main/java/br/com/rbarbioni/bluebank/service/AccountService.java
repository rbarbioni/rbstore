package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.model.enums.Operation;
import br.com.rbarbioni.bluebank.repository.AccountRepository;
import br.com.rbarbioni.bluebank.secure.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by renan on 10/02/2017.
 */

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountHistoryService accountHistoryService;

    private final JWTService jwtService;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountHistoryService accountHistoryService, JWTService jwtService) {
        this.accountRepository = accountRepository;
        this.accountHistoryService = accountHistoryService;
        this.jwtService = jwtService;
    }

    public Account save (final Account account){
        return this.accountRepository.save(account);
    }

    public Account findUnique(String cpf, String agencia, String numero) throws JsonProcessingException {

        Account account = this.accountRepository.findUnique(cpf, agencia, numero);
        if(account == null){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), "Conta inválida");
        }

        account.setToken(this.jwtService.encode(account));

        return account;
    }

    @Transactional
    public Account transfer(AccountTransferDto accountTransferDto) throws JsonProcessingException {

        Account source = this.findUnique(
                accountTransferDto.getSource().getCpf(),
                accountTransferDto.getSource().getAgencia(),
                accountTransferDto.getSource().getNumero()
                );

        Account destination = this.findUnique(
                accountTransferDto.getDestination().getCpf(),
                accountTransferDto.getDestination().getAgencia(),
                accountTransferDto.getDestination().getNumero()
        );

        if(source.equals(destination)){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), "Não é possivel realizar a transferência para a própria conta");
        }

        source = sacar(source, accountTransferDto.getAmount());
        source = this.save(source);
        this.accountHistoryService.save(source, Operation.TRANSFERENCIA_SAIDA, BigDecimal.valueOf(accountTransferDto.getAmount()));

        destination = depositar(destination, accountTransferDto.getAmount());

        destination = this.save(destination);

        this.accountHistoryService.save(destination, Operation.TRANSFERENCIA_ENTRADA, BigDecimal.valueOf(accountTransferDto.getAmount()));

        return source;
    }

    @Transactional
    public Account depositar(Account account, Double valor){

        account.depositar(BigDecimal.valueOf(valor));
        return save(account);
    }

    @Transactional
    public Account sacar(Account account, Double valor){

        if(account.getSaldo().doubleValue() < valor){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), String.format("Saldo insuficiente [saldo: %s]", NumberFormat.getCurrencyInstance().format(account.getSaldo())));
        }

        account.sacar(BigDecimal.valueOf(valor));

        return this.save(account);
    }
}
