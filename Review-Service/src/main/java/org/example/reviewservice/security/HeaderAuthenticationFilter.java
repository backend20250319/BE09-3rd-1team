package org.example.reviewservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("HeaderAuthenticationFilter doFilterInternal >>>>>> ");

        String userIdStr = request.getHeader("X-User-Id");
        String rolesHeader = request.getHeader("X-User-Role");

        log.info("인증 처리 중 - X-User-Id: {}, X-User-Role: {}", userIdStr, rolesHeader);
        if (userIdStr == null || userIdStr.isEmpty()) {
            log.debug("X-User-Id 헤더가 없거나 비어 있습니다. 인증이 수행되지 않고 다음 필터로 진행합니다.");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            log.warn("유효하지 않은 User ID 형식입니다: '{}'. 요청이 인증되지 않습니다.", userIdStr);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String jsonResponse = "{\"error\": \"Unauthorized\", \"message\": \"Invalid X-User-Id format.\"}";
            response.getWriter().write(jsonResponse);
            return;
        }
        List<SimpleGrantedAuthority> authorities;
        if (rolesHeader != null && !rolesHeader.isEmpty()) {
            authorities = List.of(rolesHeader.split(","))
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.trim()))
                    .collect(Collectors.toList());
        } else {
            authorities = List.of(new SimpleGrantedAuthority("GUEST"));
            log.warn("X-User-Role 헤더가 없거나 비어 있습니다. User ID: {}에 'GUEST' 권한을 부여합니다.", userIdStr);
        }

        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userIdStr, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("인증 완료 - User ID (String): {}, 부여된 권한: {}", userIdStr, authorities);
        filterChain.doFilter(request, response);
    }
}