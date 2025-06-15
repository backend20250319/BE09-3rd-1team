package com.unobnb.userservice.query.service;

import com.unobnb.userservice.command.entity.User;
import com.unobnb.userservice.command.repsoitory.UserRepository;
import com.unobnb.userservice.query.dto.UserDetailResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public UserDetailResponse getUser(Long userId) {
        User user = userRepository
            .findById(userId).orElseThrow(() ->
                new NotFoundException("유저 정보 찾지 못함"));

        return UserDetailResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .age(user.getAge())
            .grade(user.getGrade())
            .role(user.getRole())
            .phone(user.getPhone())
            .name(user.getName())
            .build();
    }
}
