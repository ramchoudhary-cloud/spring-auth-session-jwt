package com.ram.Authentication.Authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthenticationAuthorizationConfigue {
    @Bean // creating bean of BCrypt and get injected by spring's IOC
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean // filtering out register and verifyRegistrationToken form authentication
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity.csrf(csrf -> csrf.disable()) // disable for stateless APIs, enable if using sessions/forms
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/register", "/verifyRegistrationToken", "/signin")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).permitAll());
            return httpSecurity.build();
    }
}
