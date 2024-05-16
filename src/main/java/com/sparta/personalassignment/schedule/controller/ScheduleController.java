package com.sparta.personalassignment.schedule.controller;


import com.sparta.personalassignment.schedule.dto.ScheduleReqDto;
import com.sparta.personalassignment.schedule.dto.ScheduleResDto;
import com.sparta.personalassignment.schedule.entity.Schedule;
import com.sparta.personalassignment.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ScheduleReqDto reqDto) {
        try {
            ScheduleResDto savedSchedule = scheduleService.save(reqDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "일정이 저장되었습니다");
            response.put("schedule", savedSchedule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "일정 저장에 실패했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchSchedule(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(scheduleService.findById(id));
        } catch (Exception e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ResponseEntity.status(400);
            return ResponseEntity.status(httpStatus).body(Collections.singletonMap("아이디 값이 없습니다.", e.getMessage()));
        }
    }

    @GetMapping
    public List<ScheduleResDto> searchSchedules() {
        return scheduleService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@RequestParam String password, @PathVariable Long id, @RequestBody ScheduleReqDto reqDto) {
        try {
            ScheduleResDto savedSchedule = scheduleService.updateSchedule(id, password, reqDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "일정이 수정되었습니다.");
            response.put("schedule", savedSchedule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error","수정에 실패했습니다."+ e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@RequestParam String password, @PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id, password);
            String response = "일정이 삭제되었습니다.";
            return ResponseEntity.ok(response);        }
        catch (Exception e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ResponseEntity.status(400);
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }
}
