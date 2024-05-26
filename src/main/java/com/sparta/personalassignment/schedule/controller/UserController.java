package com.sparta.personalassignment.schedule.controller;


import com.sparta.personalassignment.schedule.dto.SignupReqDto;
import com.sparta.personalassignment.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

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
}
