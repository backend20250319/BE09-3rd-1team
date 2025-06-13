package com.unobnb.userservice.auth.service;

import com.unobnb.userservice.auth.dto.LoginRequestDTO;
import com.unobnb.userservice.auth.dto.TokenResponseDTO;
import com.unobnb.userservice.auth.entity.RefreshToken;
import com.unobnb.userservice.auth.repository.RefreshTokenRepository;
import com.unobnb.userservice.command.entity.User;
import com.unobnb.userservice.command.repsoitory.UserRepository;
import com.unobnb.userservice.jwt.JwtTokenProvider;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponseDTO login(LoginRequestDTO requestDTO) {
        User user = userRepository.findByUsername(requestDTO.getUsername())
            .orElseThrow(() ->
                new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호 입니다."));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호 입니다.");
        }

        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(),
            user.getRole().name(), user.getId());

        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(),
            user.getRole().name(), user.getId());

        RefreshToken refreshTokenEntity = RefreshToken.builder()
            .username(user.getUsername())
            .token(refreshToken)
            .expiryDate(
                new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()))
            .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return TokenResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public TokenResponseDTO refresh(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);
        
        log.info("[refresh service] username : {}", username);

        RefreshToken storedToken = refreshTokenRepository.findById(username)
            .orElseThrow(() -> new BadCredentialsException("해당 유저로 조회되는 리프레시 토큰 없음"));

        if (!storedToken.getToken().equals(refreshToken)) {
            throw new BadCredentialsException("리프레시 토큰 일치하지 않음");
        }

        if (storedToken.getExpiryDate().before(new Date())) {
            throw new BadCredentialsException("리프레시 토큰 유효시간 만료");
        }

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadCredentialsException("해당 리프레시 토큰을 위한 유저 없음"));

        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(),
            user.getRole().name(),
            user.getId());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(),
            user.getRole().name(), user.getId());

        RefreshToken tokenEntity = RefreshToken.builder()
            .username(user.getUsername())
            .token(refreshToken)
            .expiryDate(
                new Date(System.currentTimeMillis()
                    + jwtTokenProvider.getRefreshExpiration())
            )
            .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    public void logout(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);
        refreshTokenRepository.deleteById(username);
    }
}
