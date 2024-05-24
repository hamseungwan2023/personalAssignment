package com.sparta.personalassignment.schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname",nullable = false)
    private String nickname;

    @Column(name = "username",nullable = false, unique = true)
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~10자리여야 합니다.")
    private String username;

    @Column(name = "password",nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "대소문자와 숫자만 입력 가능합니다.")
    @Size(min = 8, max = 15, message = "길이는 8에서 15 사이여야 합니다.")
    private String password;

    @Column(name = "role",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String nickname, String username, String password, UserRoleEnum role) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
