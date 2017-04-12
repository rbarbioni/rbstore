package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.model.Login;
import br.com.rbarbioni.rbstore.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by renan on 10/04/17.
 */

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public Map<String, Object> login (@Valid @RequestBody Login login) throws JsonProcessingException {
        return loginService.login(login);
    }
}
