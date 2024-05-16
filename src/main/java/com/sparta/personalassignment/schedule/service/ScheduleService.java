package com.sparta.personalassignment.schedule.service;

import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import com.sparta.personalassignment.schedule.dto.ScheduleResDto;
import com.sparta.personalassignment.schedule.entity.Schedule;
import com.sparta.personalassignment.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //일정 생성
    public ScheduleResDto save(ScheduleReqDto reqDto) {
        Schedule schedule = new Schedule(reqDto);
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
    public ScheduleResDto updateSchedule(Long id, String password, ScheduleReqDto reqDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 아이디 값은 존재하지 않습니다."));
        if (password.equals(schedule.getPassword())) {
            schedule.update(reqDto);
            return new ScheduleResDto(schedule);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }
}
