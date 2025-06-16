package com.unobnb.userservice.query.controller;

import com.unobnb.userservice.common.ApiResponse;
import com.unobnb.userservice.query.dto.UserDetailResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User API")

public interface UserQueryControllerSwagger {

    @Operation(summary = "자신의 회원 정보 조회", description = "Authorization에 Access 토큰을 담아서 보내면 조회된다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원정보 조회 성공")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> getUserDetail(
        String userId);
}
