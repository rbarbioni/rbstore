package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.MalformedInputException;
import java.util.List;

/**
 * Created by renan on 12/02/17.
 */

@Component
public class SecurityHttpFilter extends GenericFilterBean {

    private final JWTService jwtService;
    private final AuthenticationHelper authenticationHelper;
    private final List<String> securePath;

    @Autowired
    public SecurityHttpFilter(
            @Value("#{'${security.secure-path}'.split(',')}") List<String> securePath,
            JWTService jwtService,
            AuthenticationHelper authenticationHelper) {
        this.securePath = securePath;
        this.jwtService = jwtService;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path  = httpRequest.getRequestURI();

        if(!this.securePath.contains(path)){
            chain.doFilter(request,response);
            return;
        }

        String token = httpRequest.getHeader("authorization");

        if(token == null || token.isEmpty()){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
            return;
        }

        try{
            Customer account = this.jwtService.decode(token);
            if(account != null){
                this.authenticationHelper.auth(account);
                chain.doFilter(request,response);
                final HttpSession httpSession = ((HttpServletRequest) request).getSession();
                httpSession.setAttribute("customer", account);
                httpSession.setAttribute("authorization", token);
            }
        }catch (IllegalArgumentException  | ExpiredJwtException | MalformedInputException e){
            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
        }catch (Exception e){
            this.responseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", httpResponse);
        }
    }


    private void responseError(Integer status, String message, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        writer.print(message);
        writer.close();
    }
}
