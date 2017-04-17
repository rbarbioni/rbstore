package br.com.rbarbioni.rbstore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService configurationService;

    @Mock
    private Environment environment;

    @Before
    public void before(){
        ReflectionTestUtils.setField(configurationService, "environment", environment);
        Mockito.when(environment.getProperty("publicKey")).thenReturn("kkk");
    }

    @Test
    public void findAll(){
        Map<String, Object> map = configurationService.findAll();
        Assert.assertNotNull(map);
    }
}