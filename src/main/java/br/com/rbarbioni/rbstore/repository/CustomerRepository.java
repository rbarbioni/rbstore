package br.com.rbarbioni.rbstore.repository;

import br.com.rbarbioni.rbstore.model.Customer;
import org.springframework.stereotype.Component;

/**
 * Created by renan on 05/04/2017.
 */

@Component
public class CustomerRepository {

    public Customer findByEmail(String email){
        return new Customer("joaosilva@email.com", "testemoip", "CUS-RON11BTKX4F0");
    }
}