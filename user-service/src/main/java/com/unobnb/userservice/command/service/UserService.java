package com.unobnb.userservice.command.service;

import com.unobnb.userservice.command.dto.UserCreateRequestDTO;
import com.unobnb.userservice.command.entity.User;
import com.unobnb.userservice.command.repsoitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserCreateRequestDTO requestDto) {
        User user = modelMapper.map(requestDto, User.class);
        user.setEncodedPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
    }
}
