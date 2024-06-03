package com.sparta.personalassignment.controller;

import com.sparta.personalassignment.dto.CommentReqDto;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.security.UserDetailsImpl;
import com.sparta.personalassignment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<?> createComment(
            @PathVariable Schedule scheduleId,
            @Valid @RequestBody CommentReqDto reqDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(reqDto, userDetails.getUser(), scheduleId);
    }

    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Schedule scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentReqDto reqDto,
            @AuthenticationPrincipal UserDetailsImpl userdetails) {
        return commentService.updateComment(reqDto, userdetails.getUser(), commentId, scheduleId);
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Schedule scheduleId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser(), scheduleId);
        return ResponseEntity.ok("댓글 삭제가 완료되었습니다.");
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
