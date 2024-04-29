package com.tasky.appManager.security;

import com.tasky.appManager.services.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenStore tokenStore;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, TokenStore tokenStore) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenStore = tokenStore;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider,tokenStore);

        http
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/manager/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers("/task/em/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOYEE","ROLE_ADMIN")
                        .requestMatchers("/task/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/auth/logout").authenticated()  // Ensure only authenticated users can access /logout
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add the JwtTokenFilter

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Set your allowed origins here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PATCH", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}