package com.sparta.personalassignment.schedule.service;

import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import com.sparta.personalassignment.schedule.dto.ScheduleResDto;
import com.sparta.personalassignment.schedule.entity.Schedule;
import com.sparta.personalassignment.schedule.repository.ScheduleRepository;
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

   public ScheduleResDto save (ScheduleReqDto reqDto) {
       Schedule schedule = new Schedule(reqDto);
       return new ScheduleResDto(scheduleRepository.save(schedule));
   }

   public ScheduleResDto findById(Long id) {
       return new ScheduleResDto(Objects.requireNonNull(scheduleRepository.findById(id).orElse(null)));
   }

   public List<ScheduleResDto> findAll() {
       List<Schedule> schedules = scheduleRepository.findAll();
       List<ScheduleResDto> scheduleResDtos = new ArrayList<>();
       for (Schedule schedule : schedules) {
           scheduleResDtos.add(new ScheduleResDto(schedule));
       }
       return scheduleResDtos;
   }
}
