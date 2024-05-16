package com.sparta.personalassignment.schedule.controller;


import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import com.sparta.personalassignment.schedule.dto.ScheduleResDto;
import com.sparta.personalassignment.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleResDto save (@Valid @RequestBody ScheduleReqDto reqDto) {
        return scheduleService.save(reqDto);
    }

    @GetMapping("/{id}")
    public ScheduleResDto searchSchedule (@PathVariable Long id) {
        return scheduleService.findById(id);
    }
    @GetMapping
    public List<ScheduleResDto> searchSchedules () {
        return scheduleService.findAll();
    }

    @PutMapping("/{id}")
    public ScheduleResDto updateSchedule (@RequestParam String password,@PathVariable Long id, @RequestBody ScheduleReqDto reqDto) {
        return scheduleService.updateSchedule(id,password,reqDto);
    }
}
