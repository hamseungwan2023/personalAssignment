package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.SignupReqDto;
import com.sparta.personalassignment.dto.UserInfoDto;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.entity.UserRoleEnum;
import com.sparta.personalassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public UserInfoDto signup(SignupReqDto reqDto){
        String username = reqDto.getUsername();
        String password = reqDto.getPassword();

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 이름입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(reqDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(reqDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(reqDto.getNickname(),username, password,role);
        UserInfoDto userInfo = new UserInfoDto(userRepository.save(user));
        return userInfo;
    }

}
