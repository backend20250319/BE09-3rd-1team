package com.unobnb.userservice.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDTO {

    private final String username;
    private final String password;
}
