package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by renan on 12/04/17.
 */
@Service
public class AppAuthenticationManager implements AuthenticationManager {

   private final LoginService loginService;

   private final AuthenticationHelper authenticationHelper;

   @Autowired
    public AppAuthenticationManager(LoginService loginService, br.com.rbarbioni.rbstore.security.AuthenticationHelper authenticationHelper) {
       this.loginService = loginService;
       this.authenticationHelper = authenticationHelper;
   }

    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        try {
            return this.authenticationHelper.auth(this.loginService.auth(username, password));
        } catch (JsonProcessingException e) {
            throw new AuthenticationCredentialsNotFoundException("Usuário ou senha inválidos");
        }
    }
}
