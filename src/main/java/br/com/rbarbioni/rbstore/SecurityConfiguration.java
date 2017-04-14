package br.com.rbarbioni.rbstore;

/**
 * Created by renan on 12/02/17.
 */

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final SecurityHttpFilter securityHttpFilter;
//    private final List<String> securePath;
//    private final AppAuthenticationManager appAuthenticationManager;
//    @Autowired
//    public SecurityConfiguration(
//            @Value("#{'${security.secure-path}'.split(',')}") List<String> securePath,
//            SecurityHttpFilter securityHttpFilter, AppAuthenticationManager appAuthenticationManager) {
//        this.securePath = securePath;
//        this.securityHttpFilter = securityHttpFilter;
//        this.appAuthenticationManager = appAuthenticationManager;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().cacheControl();
//        http.csrf().disable();
//        http
//                .authorizeRequests()
//                .antMatchers(securePath.stream().toArray(String[]::new)).authenticated()
//                .anyRequest()
//                .permitAll()
//                .and()
//                .addFilterBefore(securityHttpFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("joaosilva@email.com").password("testemoip").roles("USER");
//    }
//}