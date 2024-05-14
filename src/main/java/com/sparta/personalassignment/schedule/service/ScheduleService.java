package com.sparta.personalassignment.schedule.service;

import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import com.sparta.personalassignment.schedule.dto.ScheduleResDto;
import com.sparta.personalassignment.schedule.entity.Schedule;
import com.sparta.personalassignment.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

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
}
