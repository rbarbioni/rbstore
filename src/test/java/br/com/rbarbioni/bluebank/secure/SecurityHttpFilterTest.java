package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by renan on 13/02/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityHttpFilterTest {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private SecurityHttpFilter securityHttpFilter;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private FilterChain filterChain;
    private Account account;

    @Before
    public void init () throws IOException {

        account = new Account("31449881114", "1500", "50010", "123123");
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        PrintWriter printWriter = Mockito.mock(PrintWriter.class);
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/api/account");

        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(jwtService.encode(account));

    }

    @Test
    public void dofilterTest () throws IOException, ServletException {
        this.securityHttpFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void dofilterUnsafePathTest () throws IOException, ServletException {
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/index.html");
        this.securityHttpFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void dofilterInvalidTokenTest () throws IOException, ServletException {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("XXXXXXXXXXXXXXXX");
        this.securityHttpFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void dofilterTokenNullTest () throws IOException, ServletException {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn(null);
        this.securityHttpFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void dofilterTokenExpiratedTest () throws IOException, ServletException {
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKcWRHa2lPaUl4SWl3aWNHRjViRzloWkNJNkludGNJbWxrWENJNk1TeGNJbU55WldGMFpXUkVZWFJsWENJNk1UUTRNekl6TmpBd01EQXdNQ3hjSW0xdlpHbG1hV1ZzWkVSaGRHVmNJam94TkRnMk9UVTVOemMwTmpjeUxGd2lZM0JtWENJNlhDSXpNVFEwT1RnNE1URXhORndpTEZ3aVlXZGxibU5wWVZ3aU9sd2lNVFV3TUZ3aUxGd2liblZ0WlhKdlhDSTZYQ0kxTURBeE1Gd2lMRndpYzJGc1pHOWNJam8xTURBdU1EQjlJaXdpWlhod0lqb3hORGcyT1RjNE5qVTVmUS50NzRlb1ZwSEJfZFVtbFd0c0xJUG9qMnF1YVN1YlZtNHJsdTVNc0xjLXNj");
        this.securityHttpFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }
}
