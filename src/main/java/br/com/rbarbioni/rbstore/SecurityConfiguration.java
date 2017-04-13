package br.com.rbarbioni.rbstore;

import br.com.rbarbioni.rbstore.security.AppAuthenticationManager;
import br.com.rbarbioni.rbstore.security.SecurityHttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Created by renan on 12/02/17.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityHttpFilter securityHttpFilter;
    private final List<String> securePath;
    private final AppAuthenticationManager appAuthenticationManager;
    @Autowired
    public SecurityConfiguration(
            @Value("#{'${security.secure-path}'.split(',')}") List<String> securePath,
            SecurityHttpFilter securityHttpFilter, AppAuthenticationManager appAuthenticationManager) {
        this.securePath = securePath;
        this.securityHttpFilter = securityHttpFilter;
        this.appAuthenticationManager = appAuthenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl();
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers(securePath.stream().toArray(String[]::new)).authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .addFilterBefore(securityHttpFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("joaosilva@email.com").password("testemoip").roles("USER");
    }
}