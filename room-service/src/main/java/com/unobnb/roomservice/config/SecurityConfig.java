package com.unobnb.roomservice.config;

import com.unobnb.roomservice.security.HeaderAuthenticationFilter;
import com.unobnb.roomservice.security.RestAccessDeniedHandler;
import com.unobnb.roomservice.security.RestAuthenticationEntryPoint;
import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;


    private final HeaderAuthenticationFilter headerAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(restAuthenticationEntryPoint)
                                .accessDeniedHandler(restAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()


                        .requestMatchers(HttpMethod.GET, "/rooms/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rooms").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST, "/rooms/batch").hasAnyRole("SELLER", "CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/rooms/**").hasRole("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/rooms/**").hasRole("SELLER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }
}
