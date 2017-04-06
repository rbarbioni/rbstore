package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.LoginDto;
import br.com.rbarbioni.bluebank.secure.AuthenticationHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by renan on 12/02/17.
 */

@Service
public class LoginService {

    private final AccountService accountService;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public LoginService(AccountService accountService, AuthenticationHelper authenticationHelper) {
        this.accountService = accountService;
        this.authenticationHelper = authenticationHelper;
    }

    public Account login (LoginDto loginDto) throws JsonProcessingException {

        Account account = this.accountService.findUnique(loginDto.getCpf(), loginDto.getAgencia(), loginDto.getNumero() );

        if(account == null || !account.getPassword().equals(loginDto.getPassword())){
            throw new BlueBankException(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
        }

        Authentication authentication = this.authenticationHelper.auth(account);
        return (Account) authentication.getDetails();
    }
}
