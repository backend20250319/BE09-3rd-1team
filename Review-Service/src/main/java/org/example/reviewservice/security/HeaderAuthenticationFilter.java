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
        String userId = request.getHeader("X-User-Id");
        String rolesHeader = request.getHeader("X-User-Role");

        log.info("인증을 처리 중입니다.", userId, rolesHeader);

        if (userId != null && !userId.isEmpty()) {
            List<SimpleGrantedAuthority> authorities = null;
            if (rolesHeader != null && !rolesHeader.isEmpty()) {
                authorities = List.of(rolesHeader.split(","))
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.trim()))
                        .collect(Collectors.toList());
            } else {
                authorities = List.of(new SimpleGrantedAuthority("GUEST"));
                log.warn("userId가 없습니다.", userId);
            }
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("인증되었습니다.", userId, rolesHeader);
        } else {
            log.debug("User-Id가 없거나 비어 있습니다.");
        }
        filterChain.doFilter(request, response);
    }
}
