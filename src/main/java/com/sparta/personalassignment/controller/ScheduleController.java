package com.sparta.personalassignment.controller;

import com.sparta.personalassignment.dto.ScheduleReqDto;
import com.sparta.personalassignment.dto.ScheduleResDto;
import com.sparta.personalassignment.security.UserDetailsImpl;
import com.sparta.personalassignment.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Value("${upload.path}")
    private String filepath = System.getProperty("user.dir");

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestPart(value = "schedule") ScheduleReqDto reqDto,
                                  @Valid @RequestPart(value = "file", required = false) MultipartFile file,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            ScheduleResDto savedSchedule = scheduleService.save(reqDto, userDetails.getUser(), file, filepath);
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "일정 저장에 실패했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> searchSchedule(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(scheduleService.findById(id));
        } catch (Exception e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            ResponseEntity.status(400);
            return ResponseEntity.status(httpStatus).body(Collections.singletonMap("아이디 값이 없습니다.", e.getMessage()));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List> searchSchedules() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(
            @RequestParam String password,
            @PathVariable Long id,
            @Valid @RequestPart(value = "schedule") ScheduleReqDto reqDto,
            @Valid @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ScheduleResDto savedSchedule = scheduleService.updateSchedule(id, password, reqDto, userDetails.getUser(), file, filepath);
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "수정에 실패했습니다." + e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(
            @PathVariable Long id,
            @RequestParam String password,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id, password, userDetails.getUser());
        return ResponseEntity.ok("일정 삭제가 완료되었습니다.");
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
