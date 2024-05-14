package com.sparta.personalassignment.schedule.entity;


import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

    @Column(name = "person", nullable = false)
    private String person;

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
}
