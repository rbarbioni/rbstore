package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by renan on 09/04/17.
 */

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findByEmail(final String email){
        return this.customerRepository.findByEmail(email);
    }
}