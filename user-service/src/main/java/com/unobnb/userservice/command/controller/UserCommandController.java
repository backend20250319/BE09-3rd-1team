package com.unobnb.userservice.command.controller;

import com.unobnb.userservice.command.dto.UserCreateRequestDTO;
import com.unobnb.userservice.command.service.UserService;
import com.unobnb.userservice.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserCommandController implements UserCommandControllerSwagger {

    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> register(
        @RequestBody UserCreateRequestDTO requestDto) {

        userService.registerUser(requestDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
