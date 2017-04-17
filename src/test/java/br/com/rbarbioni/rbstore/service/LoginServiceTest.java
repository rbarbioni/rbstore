package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.exception.BusinessException;
import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.security.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private JWTService jwtService;

    @Mock
    private CustomerService customerService;

    @Before
    public void before() throws JsonProcessingException {
        Mockito.when(jwtService.encode(Matchers.any(Customer.class))).thenReturn("token");
        Mockito.when(customerService.findByEmail("joaosilva@email.com")).thenReturn(new Customer("joaosilva@email.com", "123", "123"));
    }

    @Test
    public void auth() throws JsonProcessingException {
        Customer customer = this.loginService.auth("joaosilva@email.com", "123");
        Assert.assertNotNull(customer);
    }

    @Test
    public void login() throws IOException {
        Map<String, Object> customer = this.loginService.login(new Customer("joaosilva@email.com", "123", "123"));
        Assert.assertNotNull(customer);
        Assert.assertEquals(customer.get("token"), "token");
    }

    @Test(expected = BusinessException.class)
    public void logininvalid() throws IOException {
        Map<String, Object> customer = this.loginService.login(new Customer("joaosilva@email.com1", "1231", "123"));
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void authInvalidPassword() throws JsonProcessingException {
        Customer customer = this.loginService.auth("joaosilva@email.com", "1231");
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void authInvalidEmail() throws JsonProcessingException {
        Customer customer = this.loginService.auth("joaosilva@email.com1", "1231");
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void authNullEmailAndPassword() throws JsonProcessingException {
        Customer customer = this.loginService.auth(null, null);
        Assert.assertNotNull(customer);
    }
}