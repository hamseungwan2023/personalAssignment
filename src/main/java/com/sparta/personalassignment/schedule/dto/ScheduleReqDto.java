package com.sparta.personalassignment.schedule.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleReqDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String detail;

    @NotBlank(message = "담당자는 필수 입력값입니다.")
    @Email
    private String person;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
