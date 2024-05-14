package com.sparta.personalassignment.schedule.dto;

import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class ScheduleReqDto {
    @NotNull
    private String title;
    @NotNull
    private String detail;
    @NotNull
    private String person;
    @NotNull
    private String password;
}
