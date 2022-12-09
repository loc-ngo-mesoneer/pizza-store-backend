package io.locngo.pizza.store.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.locngo.pizza.store.auth.filter.JwtAuthorizationFilter;
import io.locngo.pizza.store.auth.web.handler.CustomAccessDeniedHandler;
import io.locngo.pizza.store.auth.web.handler.CustomAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthorizationFilter jwtAuthorizationFilter,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) throws Exception {
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET, "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**", "/error")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .build();
    }
}
