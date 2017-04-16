package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by renan on 12/02/17.
 */

@Component
public class AuthenticationHelper {

    private final JWTService jwtService;

    public AuthenticationHelper(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public Authentication auth (Customer account) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword(), null);
        authenticationToken.setDetails(account);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);
        return authenticationToken;
    }
}
