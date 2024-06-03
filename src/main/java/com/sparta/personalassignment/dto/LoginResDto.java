package com.sparta.personalassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    private String username;
    private String accessToken;
    private String refreshToken;
}
