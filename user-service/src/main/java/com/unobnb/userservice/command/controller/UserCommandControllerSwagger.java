package com.unobnb.userservice.command.controller;

import com.unobnb.userservice.command.dto.UserCreateRequestDTO;
import com.unobnb.userservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User API")
public interface UserCommandControllerSwagger {

    @Operation(summary = "회원 가입", description = "아이디, 비밀번호가 있어야함")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공")
    public ResponseEntity<ApiResponse<Void>> register(UserCreateRequestDTO requestDto);

}
