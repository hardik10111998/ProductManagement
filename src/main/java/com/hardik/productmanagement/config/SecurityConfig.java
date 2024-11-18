package com.hardik.productmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/h2-console/**").permitAll() // Allow Swagger access
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true) // Redirect to Swagger on login success
                )
                .httpBasic(); // Support basic auth for API testing

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOpPasswordEncoder for simplicity (not recommended for production)
        return NoOpPasswordEncoder.getInstance();
    }
}