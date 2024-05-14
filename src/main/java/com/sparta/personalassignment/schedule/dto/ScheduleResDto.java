package com.sparta.personalassignment.schedule.dto;

import com.sparta.personalassignment.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResDto {
    private final long id;
    private final String title;
    private final String detail;
    private final String person;
    private final String password;
    private final LocalDate date;

    public ScheduleResDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.detail = schedule.getDetail();
        this.person = schedule.getPerson();
        this.password = schedule.getPassword();
        this.date = schedule.getDate().toLocalDate();
    }
}
