// This file is part of TrackMyStacks. It defines the SecurityConfig class, which configures Spring Security for the application. It includes a bean for password encoding using BCrypt and a security filter chain that defines access rules for different endpoints, form login configuration, and logout behavior. The CSRF protection is disabled for simplicity in this example, but it should be properly configured in a production application.
package com.sohaib.trackmystacks.config;

// Importing necessary classes for Spring Security configuration
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// The @Configuration annotation indicates that this class is a source of bean definitions for the application context. The @EnableWebSecurity annotation enables Spring Security's web security support and provides the Spring MVC integration.
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // This bean defines the password encoder to be used for hashing passwords. In this case, we are using BCryptPasswordEncoder, which is a strong hashing algorithm that includes a salt to protect against rainbow table attacks.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());  // Simplified for now. In production, configure CSRF properly!
        
        return http.build();
    }
}