package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.ScheduleReqDto;
import com.sparta.personalassignment.dto.ScheduleResDto;
import com.sparta.personalassignment.entity.File;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleRepository scheduleRepository;
    private final FileHandlerService fileHandlerService;
    private final ValidationService validationService;

    public ScheduleService(ScheduleRepository scheduleRepository, FileHandlerService fileHandlerService, ValidationService validationService) {
        this.scheduleRepository = scheduleRepository;
        this.fileHandlerService = fileHandlerService;
        this.validationService = validationService;
    }

    //일정 생성
    public ScheduleResDto save(ScheduleReqDto reqDto,
                               User user,
                               MultipartFile multipartFile,
                               String filepath) {
        File file = null;
        if (multipartFile != null) {
            file = fileHandlerService.FileHandler(multipartFile, filepath, true, null);
        }
        Schedule schedule = reqDto.toSchedule(user, file);

        return new ScheduleResDto(scheduleRepository.save(schedule));

    }

    //일정 조회
    public ScheduleResDto findById(Long id) {
        return new ScheduleResDto(Objects.requireNonNull(scheduleRepository.findById(id).orElse(null)));
    }

    //일정 목록 조회
    public List<ScheduleResDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResDto> scheduleResDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleResDtos.add(new ScheduleResDto(schedule));
        }
        return scheduleResDtos;
    }

    //일정 수정
    @Transactional
    public ScheduleResDto updateSchedule(Long id,
                                         String password,
                                         ScheduleReqDto reqDto,
                                         User user,
                                         MultipartFile multipartFile,
                                         String filepath) {
        validationService.validUser(user.getId());
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 일정은 존재하지 않습니다.."));
        File file = null;
        if (password.equals(user.getPassword())) {
            schedule.update(reqDto);
            if (multipartFile != null) {
                Long fileId = schedule.getFile() != null ? schedule.getFile().getId() : null;
                file = fileHandlerService.FileHandler(multipartFile, filepath, false, fileId);
            }
            return new ScheduleResDto(schedule);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }

    //일정 삭제
    public void deleteSchedule(Long id,
                               String password,
                               User user) {
        validationService.validUser(user.getId());
        validationService.validSchedule(id);
        if (password.equals(user.getPassword())) {
            scheduleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
