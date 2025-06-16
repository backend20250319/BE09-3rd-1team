package com.unobnb.userservice.auth.controller;


import com.unobnb.userservice.auth.dto.LoginRequestDTO;
import com.unobnb.userservice.auth.dto.TokenResponseDTO;
import com.unobnb.userservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth API")
public interface AuthControllerSwagger {

    @Operation(summary = "로그인 (토큰 생성 및 저장)", description = "아이디, 비밀번호 정보가 일치해야함")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> loin(
        LoginRequestDTO requestDTO);

    @Operation(summary = "리프레쉬, 액세스 토큰 재발급", description = "쿠키에 리프레쉬 토큰이 있어야함")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> refresh(
        String refreshToken);

    @Operation(summary = "로그아웃 (리프레쉬 토큰 삭제)", description = "쿠키에 리프레쉬 토큰이있으면 삭제한다.")
    public ResponseEntity<ApiResponse<Void>> logout(
        String refreshToken);
}
