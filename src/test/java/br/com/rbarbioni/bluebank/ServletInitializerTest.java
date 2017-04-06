package br.com.rbarbioni.bluebank;

import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by renan on 13/02/17.
 */
public class ServletInitializerTest {

    @Test
    public void test(){
        ServletInitializer servletInitializer = new ServletInitializer();
        servletInitializer.configure(new SpringApplicationBuilder(null));

    }
}
