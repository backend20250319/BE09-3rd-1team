package com.unobnb.userservice.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "토큰 응답 DTO")
@Getter
@Builder
public class TokenResponseDTO {

    @Schema(description = "Access 토큰", example = "String")
    private String accessToken;
    @Schema(description = "refresh 토큰", example = "String")
    private String refreshToken;
}
