package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MoipService moipService;

    @Before
    public void before() throws IOException {
        Mockito.when(customerRepository.findByEmail("joaosilva@email.com")).thenReturn(new Customer("joaosilva@email.com", "testemoip", "123"));
        Map map = new HashMap();
        map.put("email", "joaosilva@email.com");
        Mockito.when(moipService.findCustomer("123")).thenReturn(map);
    }

    @Test
    public void findByEmail(){
        Customer customer = this.customerService.findByEmail("joaosilva@email.com");
        Assert.assertNotNull(customer);
    }

    @Test
    public void findCustomer() throws IOException {
        Map<String, Object> customer = this.customerService.findCustomer("123");
        Assert.assertNotNull(customer);
        Assert.assertTrue(customer.containsKey("email"));
    }
}