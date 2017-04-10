package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by renan on 09/04/17.
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/{email}", method = {RequestMethod.GET})
    public Customer findByEmail (@PathVariable String email) {
        return this.customerService.findByEmail(email);
    }
}