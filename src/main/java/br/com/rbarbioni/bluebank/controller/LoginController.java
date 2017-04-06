package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.LoginDto;
import br.com.rbarbioni.bluebank.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by renan on 12/02/17.
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public Account login (@NotNull @RequestBody LoginDto loginDto) throws JsonProcessingException {
        return this.loginService.login(loginDto);
    }
}
