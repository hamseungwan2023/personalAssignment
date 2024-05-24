package com.sparta.personalassignment.schedule.dto;

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

}
