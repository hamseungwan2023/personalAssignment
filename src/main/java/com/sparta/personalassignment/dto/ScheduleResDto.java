package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResDto {
    private final long id;
    private final String title;
    private final String detail;

    public ScheduleResDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.detail = schedule.getDetail();
    }
}