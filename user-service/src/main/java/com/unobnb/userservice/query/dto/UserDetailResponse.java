package com.unobnb.userservice.query.dto;

import com.unobnb.userservice.command.entity.UserGrade;
import com.unobnb.userservice.command.entity.UserRole;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserResponse {

    private Long id;
    private String userid;
    private UserRole role;
    private String name;
    private int age;
    private String phone;
    private UserGrade grade;
}
