package com.hardik.productmanagement.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Create an in-memory user with username: user and password: 12345
        UserDetails user = User.withUsername("user")
                .password("12345")
                .roles("USER") // Assign role USER
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
