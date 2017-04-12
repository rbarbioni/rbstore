package br.com.rbarbioni.rbstore;

import br.com.rbarbioni.rbstore.security.SecurityHttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by renan on 12/02/17.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityHttpFilter securityHttpFilter;

    @Autowired
    public SecurityConfiguration(SecurityHttpFilter securityHttpFilter) {
        this.securityHttpFilter = securityHttpFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl();
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/api/order/**", "/api/customer/**").authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .addFilterAfter(securityHttpFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("joaosilva@email.com").password("testemoip").roles("USER");
    }
}