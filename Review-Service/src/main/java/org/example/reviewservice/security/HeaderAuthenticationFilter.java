package org.example.reviewservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 임포트
import org.springframework.security.core.context.SecurityContextHolder;     // 임포트
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken; // 임포트
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors; // 역할 처리를 위해 임포트

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String userId = request.getHeader("X-User-Id");
        String rolesHeader = request.getHeader("X-User-Role"); // 여러 역할을 처리하기 위해 rolesHeader로 변경

        log.info("userId: {}와 roles: {}에 대한 인증을 처리 중입니다.", userId, rolesHeader);

        if (userId != null && !userId.isEmpty()) { // userId가 null이 아니고 비어있지 않은지 확인
            List<SimpleGrantedAuthority> authorities = null;
            if (rolesHeader != null && !rolesHeader.isEmpty()) {
                // 역할이 쉼표로 구분되어 있다고 가정합니다 (예: "USER,ADMIN")
                authorities = List.of(rolesHeader.split(","))
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.trim()))
                        .collect(Collectors.toList());
            } else {
                // 역할이 제공되지 않은 경우 기본 "GUEST" 역할을 할당합니다.
                // 정책에 따라 예외를 던지거나 다른 기본값을 할당할 수 있습니다.
                // 현재는 역할이 제공되지 않으면 기본 'GUEST' 역할을 할당합니다.
                authorities = List.of(new SimpleGrantedAuthority("GUEST"));
                log.warn("userId: {}에 대한 X-User-Role 헤더가 없습니다. 'GUEST' 역할을 할당합니다.", userId);
            }

            // 인증 객체를 생성합니다. userId가 주체가 됩니다.
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(userId, null, authorities);

            // SecurityContextHolder에 인증 객체를 설정합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("헤더를 통해 사용자 '{}'가 역할 '{}'로 성공적으로 인증되었습니다.", userId, rolesHeader);
        } else {
            log.debug("X-User-Id 헤더가 없거나 비어 있습니다. 이 요청에 대한 인증을 건너뜁니다.");
        }
        filterChain.doFilter(request, response);
    }
}
