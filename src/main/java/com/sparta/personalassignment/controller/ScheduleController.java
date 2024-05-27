package com.sparta.personalassignment.controller;

import com.sparta.personalassignment.dto.ScheduleReqDto;
import com.sparta.personalassignment.dto.ScheduleResDto;
import com.sparta.personalassignment.service.ScheduleService;
import com.sparta.personalassignment.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Value("${upload.path}")
    private String filepath = System.getProperty("user.dir");

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestPart ScheduleReqDto reqDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ScheduleResDto savedSchedule = scheduleService.save(reqDto,userDetails.getUser(),file,filepath);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "일정이 저장되었습니다");
            response.put("schedule", savedSchedule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
    public ResponseEntity<List> searchSchedules() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(
            @RequestParam String password,
            @PathVariable Long id,
            @Valid @RequestBody ScheduleReqDto reqDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails ) {

        try {
            ScheduleResDto savedSchedule = scheduleService.updateSchedule(id, password, reqDto,userDetails.getUser());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "일정이 수정되었습니다.");
            response.put("schedule", savedSchedule);
            return ResponseEntity.ok(response);
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
        scheduleService.deleteSchedule(id, password,userDetails.getUser());
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
