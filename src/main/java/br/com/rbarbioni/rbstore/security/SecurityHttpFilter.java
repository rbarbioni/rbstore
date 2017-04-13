package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by renan on 12/02/17.
 */

@Component
public class SecurityHttpFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTService jwtService;
    private final ObjectMapper objectMapper;
    private final AuthenticationHelper authenticationHelper;
    private final List<String> securePath;
    private final AppAuthenticationManager appAuthenticationManager;

    @Autowired
    public SecurityHttpFilter(
            @Value("#{'${security.secure-path}'.split(',')}") List<String> securePath,
            JWTService jwtService,
            ObjectMapper objectMapper,
            AuthenticationHelper authenticationHelper, AppAuthenticationManager appAuthenticationManager) {
        this.securePath = securePath;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.authenticationHelper = authenticationHelper;
        this.appAuthenticationManager = appAuthenticationManager;
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String path  = httpRequest.getRequestURI();
//
//        if(!this.securePath.contains(path)){
//            chain.doFilter(request,response);
//            return;
//        }
//
//        String token = httpRequest.getHeader("Authorization");
//
//        if(token == null || token.isEmpty()){
//            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
//            return;
//        }
//
//        try{
//            Customer account = this.jwtService.decode(token);
//            if(account != null){
//                this.authenticationHelper.auth(account);
//                chain.doFilter(request,response);
//            }
//        }catch (IllegalArgumentException  | ExpiredJwtException e){
//            this.responseError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", httpResponse);
//        }catch (Exception e){
//            this.responseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", httpResponse);
//        }
//    }
//
//
//    private void responseError(Integer status, String message, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//        response.setStatus(status);
//        PrintWriter writer = response.getWriter();
//        writer.print(message);
//        writer.close();
//    }

    @Autowired
    public void setAuthenticationManager(AppAuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws AuthenticationException {
        String token = httpRequest.getHeader("Authorization");
        try {
            Customer account = this.jwtService.decode(token);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword());
            setDetails(httpRequest, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new AuthenticationServiceException("Unauthorized");
    }
}
