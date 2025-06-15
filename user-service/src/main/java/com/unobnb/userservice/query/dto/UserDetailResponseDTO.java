package com.unobnb.userservice.query.dto;

import com.unobnb.userservice.command.entity.UserGrade;
import com.unobnb.userservice.command.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Schema(description = "회원정보 응답 DTO")
@Getter
@Builder
public class UserDetailResponseDTO {

    @Schema(description = "회원번호 (PK)", example = "1")
    private Long id;
    @Schema(description = "아이디", example = "user01")
    private String username;
    @Schema(description = "회원 종류", example = "CUSTOMER")
    private UserRole role;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "나이", example = "26")
    private int age;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "등급", example = "GOLD")
    private UserGrade grade;
}
