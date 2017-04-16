package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.exception.BusinessException;
import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.security.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 10/04/17.
 */

@Service
public class LoginService {

    private static final String LOGIN_ERROR = "Usuário ou senha inválidos";

    private final CustomerService customerService;

    private final JWTService jwtService;

    @Autowired
    public LoginService(CustomerService customerService, JWTService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    public Map<String, Object> login (Customer customer) throws IOException {

        customer = auth(customer.getEmail(), customer.getPassword());
        Map<String, Object> map = new HashMap();
        map.put("token", this.jwtService.encode(customer));
        map.put("customer", this.customerService.findCustomer(customer.getPaymentId()));
        return map;
    }

    public Customer auth (String email, String password) throws JsonProcessingException {
        Customer customer = this.customerService.findByEmail(email);

        if(customer == null || !password.equals(customer.getPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), LOGIN_ERROR);
        }

        return customer;
    }
}
