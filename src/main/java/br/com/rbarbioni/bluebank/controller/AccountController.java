package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.service.AccountHistoryService;
import br.com.rbarbioni.bluebank.service.AccountService;
import br.com.rbarbioni.bluebank.util.Cpf;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by renan on 10/02/2017.
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    private final AccountHistoryService accountHistoryService;

    @Autowired
    public AccountController(AccountService accountService, AccountHistoryService accountHistoryService) {
        this.accountService = accountService;
        this.accountHistoryService = accountHistoryService;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public Account getConta (
            @NotNull @NotEmpty @Cpf @RequestParam String cpf,
            @NotNull @NotEmpty @RequestParam String agencia,
            @NotNull @NotEmpty @RequestParam String numero) throws JsonProcessingException {
        return this.accountService.findUnique(cpf, agencia, numero);
    }

    @RequestMapping(value = "transfer", method = {RequestMethod.POST})
    public Account transfer (
            @NotNull @RequestBody AccountTransferDto accountTransferDto) throws JsonProcessingException {

        return this.accountService.transfer(accountTransferDto);
    }

    @RequestMapping(value = "statement", method = {RequestMethod.GET})
    public List<AccountHistory> statement (
            @NotNull @NotEmpty @Cpf @RequestParam String cpf,
            @NotNull @NotEmpty @RequestParam String agencia,
            @NotNull @NotEmpty @RequestParam String numero){

        return this.accountHistoryService.find(cpf, agencia, numero);

    }
}
