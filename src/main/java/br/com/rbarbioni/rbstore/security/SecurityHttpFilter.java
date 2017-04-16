package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.model.ResponseError;
import br.com.rbarbioni.rbstore.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.MalformedInputException;

/**
 * Created by renan on 12/02/17.
 */

@Service
public class SecurityHttpFilter extends GenericFilterBean {

    private final JWTService jwtService;
    private final AuthenticationHelper authenticationHelper;
    private final ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public SecurityHttpFilter(
            JWTService jwtService,
            AuthenticationHelper authenticationHelper,
            ObjectMapper objectMapper, CustomerRepository customerRepository) {

        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.authenticationHelper = authenticationHelper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path  = httpRequest.getRequestURI();

        if(!path.contains("/secure/")){
            chain.doFilter(request,response);
            return;
        }

        String token = httpRequest.getHeader("authorization");

        if(token == null || token.isEmpty()){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Invalid Authorization Token", path, httpResponse);
            return;
        }

        try{
            String accountId = this.jwtService.decode(token);
            if(accountId == null){
                this.responseError(HttpStatus.UNAUTHORIZED.value(), "Invalid Authorization Token", path, httpResponse);
            }

            Customer customer = this.customerRepository.findByEmail(accountId);

            if(customer == null){
                this.responseError(HttpStatus.UNAUTHORIZED.value(), "Invalid Authorization Token", path, httpResponse);
            }

            this.authenticationHelper.auth(customer);

            chain.doFilter(request,response);
        }catch (IllegalArgumentException  | ExpiredJwtException | MalformedInputException e){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Invalid Authorization Token", path, httpResponse);
        }catch (Exception e){
            this.responseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", path, httpResponse);
        }
    }


    private void responseError(Integer status, String message, String path, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(httpStatus);
        writer.print(this.objectMapper.writeValueAsString(new ResponseError(status, httpClientErrorException.getClass().getName(), httpStatus.name(), message, path)));
        writer.flush();
        writer.close();
    }
}
