package com.sparta.personalassignment.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.personalassignment.Exception.SetErrorResponse;
import com.sparta.personalassignment.jwt.JwtUtil;
import com.sparta.personalassignment.schedule.dto.LoginReqDto;
import com.sparta.personalassignment.schedule.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private SetErrorResponse setErrorResponse = new SetErrorResponse();
    private String username;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        String refreshToken = jwtUtil.createRefreshToken(username, role);

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
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        String accessToken = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);
        String refreshToken = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);
        if(accessToken != null){
            if(jwtUtil.validateToken(accessToken,response)){
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
            } else if (!jwtUtil.validateToken(accessToken,response) && refreshToken != null) {
                String newAccessToken = jwtUtil.createToken(username, UserRoleEnum.USER);
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, refreshToken);
            }else{
                setErrorResponse.setErrorMessage(response,"모든 토큰 값이 만료 되었습니다.");
            }
        }else{
            setErrorResponse.setErrorMessage(response,"로그인에 실패하셨습니다.");
        }
    }
}