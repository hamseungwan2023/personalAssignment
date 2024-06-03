package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.Comment;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDto {
    @NotBlank(message = "댓글내용은 필수 입력 값 입니다.")
    private String content;

    public Comment toComment (User user, Schedule schedule) {
        return Comment.builder()
                .content(content)
                .schedule(schedule)
                .user(user)
                .build();
    }
}
