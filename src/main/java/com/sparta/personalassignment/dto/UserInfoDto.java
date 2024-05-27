package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
