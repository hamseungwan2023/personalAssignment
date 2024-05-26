package com.sparta.personalassignment.schedule.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class SignupReqDto {
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~10자리여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}+$", message = "비밀번호는 영어 소문자와 숫자만 사용하여 8~15자리여야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값 입니다.")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}
