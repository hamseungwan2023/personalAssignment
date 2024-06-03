package com.sparta.personalassignment.service;

import com.sparta.personalassignment.entity.RefreshToken;
import com.sparta.personalassignment.entity.UserRoleEnum;
import com.sparta.personalassignment.jwt.JwtUtil;
import com.sparta.personalassignment.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void saveRefreshToken(String refreshToken) {
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByHashedToken(refreshToken);
        existingTokenOpt.ifPresent(refreshTokenRepository::delete);
        RefreshToken tokenEntity = new RefreshToken(refreshToken);
        refreshTokenRepository.save(tokenEntity);
    }

    public Map<String, String> refreshAccessToken(String refreshToken) {
        String encodeToken = Base64.getEncoder().encodeToString(refreshToken.getBytes());
        RefreshToken tokenEntity = refreshTokenRepository.findByHashedToken(encodeToken)
                .orElseThrow(() -> new RuntimeException("토큰값이 없습니다."));

        byte[] decodeToken = Base64.getDecoder().decode(encodeToken);
        String decodeTokenString = new String(decodeToken);

        String username = jwtUtil.getUsernameFromToken(decodeTokenString);
        UserRoleEnum role = jwtUtil.getRoleFromToken(decodeTokenString);

        String newAccessToken = jwtUtil.createToken(username, role);
        String newRefreshToken = jwtUtil.createRefreshToken(username, role);

        String hashedRefreshToken = Base64.getEncoder().encodeToString(newRefreshToken.getBytes());

        refreshTokenRepository.delete(tokenEntity);
        refreshTokenRepository.save(new RefreshToken(hashedRefreshToken));

        Map<String, String> responseBody = new HashMap<>();

        responseBody.put("accessToken", newAccessToken);
        responseBody.put("refreshToken", newRefreshToken);
        responseBody.put("username", username);
        return responseBody;
    }
}
