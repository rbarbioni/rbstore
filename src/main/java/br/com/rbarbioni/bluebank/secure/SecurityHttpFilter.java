package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.ResponseErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by renan on 12/02/17.
 */

@Component
public class SecurityHttpFilter extends GenericFilterBean {

    private final JWTService jwtService;

    private final ObjectMapper objectMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public SecurityHttpFilter(JWTService jwtService, ObjectMapper objectMapper, AuthenticationHelper authenticationHelper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path  = httpRequest.getRequestURI();

        if(path.contains("/login") || !path.contains("/api")){
            chain.doFilter(request,response);
            return;
        }

        String token = httpRequest.getHeader("Authorization");

        if(token == null || token.isEmpty()){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
            return;
        }

        try{
            Account account = this.jwtService.decode(token);
            if(account != null){
                this.authenticationHelper.auth(account);
                chain.doFilter(request,response);
            }
        }catch (IllegalArgumentException  | ExpiredJwtException e){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
        }catch (Exception e){
            this.responseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", httpResponse);
        }
    }


    private void responseError(Integer status, String message, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        writer.print(this.objectMapper.writeValueAsString(new ResponseErrorDto(message, status)));
        writer.close();
    }
}
