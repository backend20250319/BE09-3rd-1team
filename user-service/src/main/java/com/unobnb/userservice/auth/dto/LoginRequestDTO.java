package com.unobnb.userservice.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "로그인 요청 DTO")
@Getter
@RequiredArgsConstructor
public class LoginRequestDTO {

    @Schema(description = "아이디", example = "user01")
    private final String username;
    @Schema(description = "비밀번호", example = "pass01")
    private final String password;
}
