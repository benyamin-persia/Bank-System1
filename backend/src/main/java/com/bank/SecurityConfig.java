package com.bank;

import com.bank.service.BankUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.Security;
import java.util.List;


@Configuration
@EnableMethodSecurity // ¬ NEW — activates @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {
    private final BankUserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // strength=10 by default
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // Create CORS config
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Allow React app origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Allow methods
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Create source
        source.registerCorsConfiguration("/**", configuration); // Apply to all routes
        return source; // Return source
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()  // add this
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/customers/add").hasRole("ADMIN")
                        .requestMatchers("/register", "/verify").permitAll() // NEW
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .requestMatchers("/auth/login").permitAll() // allows jwt login endpoint


                        .anyRequest().authenticated() // catch-all last
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/customers", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                )
                .sessionManagement(sm -> sm // configure session management
                        .sessionFixation().newSession() // create a new session after login
                        .maximumSessions(1) // allow only one session per user
                        .maxSessionsPreventsLogin(false) // allow new login and expire old session
                        .expiredUrl("/login?expired") // redirect when session expires
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(h -> h.frameOptions(f -> f.disable()));
        return http.build();
    }
    @Bean // Creates AuthenticationManager bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception { // Gets auth manager from Spring config
        return config.getAuthenticationManager(); // Returns AuthenticationManager bean
    }
}