package com.sparta.personalassignment.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.personalassignment.dto.LoginReqDto;
import com.sparta.personalassignment.entity.UserRoleEnum;
import com.sparta.personalassignment.jwt.JwtUtil;
import com.sparta.personalassignment.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private String username;

    private final RefreshTokenService refreshTokenService;
    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginReqDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginReqDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
            UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        String refreshToken = jwtUtil.createRefreshToken(username,role);
        String hashedToken = Base64.getEncoder().encodeToString(refreshToken.getBytes());

        refreshTokenService.saveRefreshToken(hashedToken);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("refreshToken", refreshToken);
        responseBody.put("username", username);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try{
            new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        }catch(Exception e){
            log.error(e.getMessage());
            responseBody.put("로그인에 실패했습니다", e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(401);
    }
}