package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //This was only for demostration
    // Can use any AuthenticationManager, this use case, we use in memory AuthenticationManager
//    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
//
//        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
//        filter.setAuthenticationManager(authenticationManager);
//
//        return filter;
//    }

//    This was only for demostration
//    public RestUrlParamsAuthFilter restParamsAuthFilter(AuthenticationManager authenticationManager) {
//        RestUrlParamsAuthFilter filter = new RestUrlParamsAuthFilter(new AntPathRequestMatcher("/api/**"));
//        filter.setAuthenticationManager(authenticationManager);
//
//        return filter;
//    }

//    @Bean //If we need to expose the AuthenticationManager
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    PasswordEncoder passwordEncoder(){ // override the default implementation of password encoder, {noop} is not needed
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder(); //Custom Delegating Password Encoder
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //Delegating Password Encoder
//        return new BCryptPasswordEncoder(); //Spring Security default
//        return new StandardPasswordEncoder(); //only use this encoder for legacy
//        return new LdapShaPasswordEncoder();
//        return <NoOpPasswordEncoder.getInstance()>; //only use this encoder for legacy
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationConfiguration authenticationConfiguration) throws Exception {

//        httpSecurity.addFilterBefore(restHeaderAuthFilter(authenticationConfiguration.getAuthenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(restParamsAuthFilter(authenticationConfiguration.getAuthenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();

        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() // do not use in production
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll() //this needs to show static resources on /
                        .antMatchers(HttpMethod.GET, "/api/v1/beer/**").hasAnyRole("ADMIN", "CUSTOMER", "USER")
//                        .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("ADMIN", "CUSTOMER", "USER") //another implementation
                        .mvcMatchers("/brewery/breweries").hasAnyRole("ADMIN", "CUSTOMER")
                        .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries").hasAnyRole("ADMIN", "CUSTOMER")
                        .mvcMatchers("/beers/find", "/beers/{beerId}").hasAnyRole("ADMIN", "CUSTOMER", "USER")
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().csrf().disable();

        //h2 console config
        httpSecurity.headers().frameOptions().sameOrigin();

        return httpSecurity.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() { //replace the AuthenticationManagerBuilder
//
//        UserDetails admin = User.builder()
//                .username("spring")
//                .password("{bcrypt}$2a$10$XJcJQXTRqzIdiehxRZI1X.OWg7bHIRu1Wal4JpDD7MvfMs/yfWpky") //Bcrypt
////                .password("{SSHA}EdMjVPV27Ut88qU5td1m1YDAXBl2GBE8infd8Q==") //LDAP
////                .password("kahlua")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{sha256}5cdbdd61f5fe9faf0893cad669643946e44c5f63482d4a46ad7f9d4ebc5395fd22035927fd8fdb22") //SHA-256 with PasswordEncoderFactories
////                .password("$2a$10$Seg8Cq7bEHFD2HqY4S1JAOqIV64PtDS4DAPcJ9ph8IEhe2pZIa80C") //Bcrypt
////                .password("5cdbdd61f5fe9faf0893cad669643946e44c5f63482d4a46ad7f9d4ebc5395fd22035927fd8fdb22") //SHA-256
////                .password("{SSHA}EdMjVPV27Ut88qU5td1m1YDAXBl2GBE8infd8Q==") //LDAP
////                .password("password") //using NoOpPasswordEncoder
////                .password("{noop}password") //{noop} no op password encoder
//                .roles("USER")
//                .build();
//
//        UserDetails customer = User.builder()
//                .username("scott")
//                .password("{bcrypt15}$2a$15$kMR8TNjCDu9e/hD90AAEnuBHEDWql.2P/CvuPu7dZDFyDRaK6eJtO") //Custom Encoder bcrypt15
////                .password("{ldap}{SSHA}/jzR6gR/Y+9cHI1R/kc+QiWnl9loefmy4uRBUw==") //LDAP with PasswordEncoderFactories
////                .password("tiger") //using NoOpPasswordEncoder
//                .roles("CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user, customer);
//    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withDefaultPasswordEncoder() //doc: Using this method is not considered safe for production, but is acceptable for demos and getting started.
//                .username("spring")
//                .password("kahlua")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
