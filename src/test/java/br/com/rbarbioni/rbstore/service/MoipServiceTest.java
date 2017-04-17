package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.exception.BusinessException;
import br.com.rbarbioni.rbstore.model.ResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class MoipServiceTest {

    @InjectMocks
    private MoipService moipService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void before(){
        ReflectionTestUtils.setField(moipService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(moipService, "objectMapper", new ObjectMapper());
        ReflectionTestUtils.setField(moipService, "authorization", "MOIP_TOKEN");

    }

    @Test(expected = BusinessException.class)
    public void executeMoipApi500() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.status(500).body(new ResponseError(500, "", "", "", "")));
        Map<String, Object> customer = this.moipService.findCustomer("123");
        Assert.assertNotNull(customer);
    }

    @Test
    public void findCustomer() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.ok().body("{}"));
        Map<String, Object> customer = this.moipService.findCustomer("123");
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void findCustomer400() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.badRequest().body("{}"));
        Map<String, Object> customer = this.moipService.findCustomer("123");
        Assert.assertNotNull(customer);
    }

    @Test
    public void findPaymentStatus() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.ok().body("{}"));
        Map<String, Object> customer = this.moipService.findStatus("123");
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void findPaymentStatus400() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.badRequest().body("{}"));
        Map<String, Object> customer = this.moipService.findStatus("123");
        Assert.assertNotNull(customer);
    }


    @Test
    public void requestOrder() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.ok().body("{}"));
        Map<String, Object> customer = this.moipService.requestOrder(new Object());
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void requestOrder400() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.badRequest().body("{}"));
        Map<String, Object> customer = this.moipService.requestOrder(new Object());
        Assert.assertNotNull(customer);
    }

    @Test
    public void requestPaymnent() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.ok().body("{}"));
        Map<String, Object> customer = this.moipService.requestPaymnent("123", "{}");
        Assert.assertNotNull(customer);
    }

    @Test(expected = BusinessException.class)
    public void requestPaymnent400() throws IOException {
        Mockito.when(this.restTemplate.exchange(Matchers.anyString(), Matchers.any(HttpMethod.class), Matchers.any(HttpEntity.class), Matchers.any(Class.class)))
                .thenReturn(ResponseEntity.badRequest().body("{}"));
        Map<String, Object> customer = this.moipService.requestPaymnent("123", "{}");
        Assert.assertNotNull(customer);
    }
}