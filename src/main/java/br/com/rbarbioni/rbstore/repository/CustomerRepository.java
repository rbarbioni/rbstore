package br.com.rbarbioni.rbstore.repository;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.model.DocumentType;
import br.com.rbarbioni.rbstore.model.Phone;
import br.com.rbarbioni.rbstore.model.TaxDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by renan on 05/04/2017.
 */

@Component
public class CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    public Customer findByEmail(String email){
        return new Customer(
                "João",
                "João Portador da Silva",
                "joaosilva@email.com",
                new Date(),
                new TaxDocument("12345679891", DocumentType.CPF),
                new Phone("55","11", "66778899")

        );
    }
}