package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import io.jsonwebtoken.JwtException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

/**
 * Created by renan on 17/04/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    @Before
    public void before(){
        ReflectionTestUtils.setField(jwtService, "secret", "SECRET");
    }

    @Test
    public void decodeTest() throws IOException {
        String token = this.jwtService.encode(new Customer("joaosilva@email.com", "", ""));
        String decode = jwtService.decode(token);
        Assert.assertEquals(decode, "joaosilva@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void decodeErrorTest() throws IOException {
        jwtService.decode("INVALID_TOKEN");
    }
}
