package org.example.reviewservice.security; // 패키지 이름 수정됨

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 메소드 수준 보안 활성화 (예: @PreAuthorize)
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final HeaderAuthenticationFilter headerAuthenticationFilter; // 필터 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 무상태 API를 위해 CSRF 비활성화
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 무상태 세션 사용
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(restAuthenticationEntryPoint) // 인증되지 않은 요청에 대한 사용자 정의 진입점
                                .accessDeniedHandler(restAccessDeniedHandler)         // 접근 거부에 대한 사용자 정의 핸들러
                )
                .authorizeHttpRequests(auth ->
                        auth
                                // 스웨거 UI 경로에 대한 인증되지 않은 접근 허용
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/swagger-resources/**"
                                ).permitAll()
                                // 리뷰 엔드포인트에 대한 역할 기반 접근 규칙 정의
                                // 예시:
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAnyAuthority("USER", "ADMIN") // USER 또는 ADMIN만 생성 가능
                                .requestMatchers(HttpMethod.PUT, "/reviews/**").hasAnyAuthority("USER", "ADMIN") // USER 또는 ADMIN만 업데이트 가능
                                .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasAnyAuthority("USER", "ADMIN") // USER 또는 ADMIN만 삭제 가능
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll() // 모든 리뷰 읽기 가능
                                .anyRequest().authenticated() // 그 외 모든 요청은 인증되어야 함
                )
                // Spring Security의 기본 필터 앞에 사용자 정의 헤더 인증 필터 추가
                .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}