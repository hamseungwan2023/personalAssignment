package com.sparta.personalassignment.schedule.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupReqDto {
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값 입니다.")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}
