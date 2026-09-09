package com.dimtoups.accountingApp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
        )
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/user").permitAll()
            .requestMatchers(HttpMethod.GET, "/csrf/token").permitAll()
            .anyRequest().authenticated()
        );

    return http.build();
  }
}
