package com.sparta.personalassignment.schedule.entity;


import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

    @NotBlank(message = "담당자는 필수 입력값입니다.")
    @Column(name = "person", nullable = false, length = 10)
    @Email
    private String person;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Column(name = "password", nullable = false, length = 20)
    private String password;


    @CreatedDate
    @Column(name = "date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date = LocalDateTime.now();

    public Schedule(ScheduleReqDto reqDto) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
        this.person = reqDto.getPerson();
        this.password = reqDto.getPassword();
    }
    public void update (ScheduleReqDto reqDto){
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
        this.person = reqDto.getPerson();
    }
}
