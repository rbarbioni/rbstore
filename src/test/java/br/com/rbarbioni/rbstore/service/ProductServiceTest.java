package br.com.rbarbioni.rbstore.service;

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
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private MoipService moipService;

    @Before
    public void before() throws IOException {
        ReflectionTestUtils.setField(paymentService, "objectMapper", new ObjectMapper());
        Mockito.when(moipService.findStatus(Matchers.anyString())).thenReturn(new HashMap<>());
    }

    @Test
    public void findStatus() throws IOException {
        Map<String, Object> status = this.paymentService.findStatus("123");
        Assert.assertNotNull(status);
    }

    @Test
    public void processWebHook() throws IOException {
        this.paymentService.processWebHook("data");
    }
}