package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.File;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleReqDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String detail;

    public Schedule toSchedule(User user, File file) {
        return Schedule.builder()
                .title(title)
                .detail(detail)
                .user(user)
                .file(file)
                .build();
    }
}
