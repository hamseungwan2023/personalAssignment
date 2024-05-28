package com.sparta.personalassignment.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.personalassignment.dto.SignupReqDto;
import com.sparta.personalassignment.service.RefreshTokenService;
import com.sparta.personalassignment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto reqDto, BindingResult bindingResult) {
        // Validation 예외처리
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return ResponseEntity.badRequest().body(errors);
            }
        }
        return ResponseEntity.ok(userService.signup(reqDto));
    }

    @PostMapping("/tokenRefresh")
    public ResponseEntity<?> tokenRefresh (HttpServletRequest request) throws JsonProcessingException {
        String refreshToken = request.getHeader("Refresh-Token");
        return ResponseEntity.ok(refreshTokenService.refreshAccessToken(refreshToken));
    }
}
