package com.unobnb.userservice.auth.controller;

import com.unobnb.userservice.auth.dto.LoginRequestDTO;
import com.unobnb.userservice.auth.dto.TokenResponseDTO;
import com.unobnb.userservice.auth.service.AuthService;
import com.unobnb.userservice.common.ApiResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthControllerSwagger {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> loin(
        @RequestBody LoginRequestDTO requestDTO) {
        TokenResponseDTO responseDTO = authService.login(requestDTO);

        return buildTokenResponse(responseDTO);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> refresh(
        @CookieValue String refreshToken) {
        log.info("[Post : /refresh] refreshToken : {}", refreshToken);
        TokenResponseDTO responseDTO = authService.refresh(refreshToken);
        log.info("[Post : /refresh] 성공적");

        return buildTokenResponse(responseDTO);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
        @CookieValue String refreshToken) {
        log.info("[Post : /logout] refreshToken : {}", refreshToken);
        authService.logout(refreshToken);
        log.info("[Post : /logout] 로그아웃 성공적");

        ResponseCookie cookie = createDeleteRefreshTokenCookie();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(null);
    }

    private ResponseEntity<ApiResponse<TokenResponseDTO>> buildTokenResponse(
        TokenResponseDTO tokenResponse) {
        ResponseCookie cookie = createRefreshTokenCookie(tokenResponse.getRefreshToken());

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(ApiResponse.success(tokenResponse));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)                     // HttpOnly 속성 설정 (JavaScript 에서 접근 불가)
            // .secure(true)                    // HTTPS 환경일 때만 전송 (운영 환경에서 활성화 권장)
            .path("/")                          // 쿠키 범위 : 전체 경로
            .maxAge(Duration.ofDays(7))         // 쿠키 만료 기간 : 7일
            .sameSite("Strict")                 // CSRF 공격 방어를 위한 SameSite 설정
            .build();
    }

    /* 쿠키 삭제용 설정
     * 빈 값 + maxAge=0 으로 즉시 만료시켜 브라우저에서 삭제
     */
    private ResponseCookie createDeleteRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
            .httpOnly(true)     // HttpOnly 유지
            // .secure(true)    // HTTPS 환경에서만 사용 시 주석 해제
            .path("/")          // 동일 path 범위
            .maxAge(0)          // 즉시 만료
            .sameSite("Strict") // SameSite 유지
            .build();
    }
}
