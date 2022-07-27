package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll() //this needs to show static resources on /
                        .antMatchers("/beers/find", "/beers*").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll() //another implementation
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.withDefaultPasswordEncoder() //doc: Using this method is not considered safe for production, but is acceptable for demos and getting started.
                .username("spring")
                .password("kahlua")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
