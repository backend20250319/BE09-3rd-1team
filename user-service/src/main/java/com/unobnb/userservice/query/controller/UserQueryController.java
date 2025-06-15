package com.unobnb.userservice.query.controller;

import com.unobnb.userservice.common.ApiResponse;
import com.unobnb.userservice.query.dto.UserDetailResponse;
import com.unobnb.userservice.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetail(
        @AuthenticationPrincipal String userId) {

        log.info("[GET user/detail ] userId : {}", userId);
        UserDetailResponse userDetailResponse = userQueryService.getUser(Long.parseLong(userId));

        return ResponseEntity.ok(ApiResponse.success(userDetailResponse));
    }

}
