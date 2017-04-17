package br.com.rbarbioni.rbstore.repository;

import br.com.rbarbioni.rbstore.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by renan on 05/04/2017.
 */

@Component
public class CustomerRepository {

    private final Map<String, Customer> customers = Collections.synchronizedMap(new HashMap<>());

    public CustomerRepository (){
        customers.put("joaosilva@email.com", new Customer("joaosilva@email.com", "testemoip", "CUS-RON11BTKX4F0"));
    }

    public Customer findByEmail(String email){
        return customers.get(email);
    }
}