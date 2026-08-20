package com.ram.Authentication.Authorization.configuration;

import com.ram.Authentication.Authorization.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class AuthenticationAuthorizationConfigue {
    @Bean // creating bean of BCrypt and get injected by spring's IOC
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    // JWT Token based Authentication
    // In this we register Spring Security with JWT (STATELESS) instead of Spring Security's default Session-based (STATEFUL)
    @Bean // filtering out register and verifyRegistrationToken form authentication
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) throws Exception{
            httpSecurity.csrf(csrf -> csrf.disable()) // disable for stateless APIs, enable if using sessions/forms
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/register", "/verifyRegistrationToken", "/signin")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
    }

//    // Session Based Authentication
//    @Bean // filtering out register and verifyRegistrationToken form authentication
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.csrf(csrf -> csrf.disable()) // disable for stateless APIs, enable if using sessions/forms
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "/register", "/verifyRegistrationToken", "/signin")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated()
//                )
//                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).permitAll());
//        return httpSecurity.build();
//    }
}
