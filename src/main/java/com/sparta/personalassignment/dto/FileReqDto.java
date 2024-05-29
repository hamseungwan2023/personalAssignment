package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.Schedule;
import lombok.Getter;

@Getter
public class FileReqDto {
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private Schedule schedule;

    public FileReqDto(String fileName, String filePath, String fileType, Long fileSize, Schedule schedule) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.schedule = schedule;
    }
}
