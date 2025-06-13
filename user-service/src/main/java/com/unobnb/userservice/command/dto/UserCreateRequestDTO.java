package com.unobnb.userservice.command.dto;

import com.unobnb.userservice.command.entity.UserGrade;
import com.unobnb.userservice.command.entity.UserRole;
import lombok.Getter;

@Getter
public class UserCreateRequestDTO {

    private String username;
    private String password;
    private UserRole role;
    private String name;
    private int age;
    private String phone;
    private UserGrade grade;
}
