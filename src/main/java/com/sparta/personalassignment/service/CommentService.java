package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.CommentReqDto;
import com.sparta.personalassignment.entity.Comment;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.repository.CommentRepository;
import com.sparta.personalassignment.repository.ScheduleRepository;
import com.sparta.personalassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> createComment(CommentReqDto reqDto, User user, Schedule scheduleId) {
        validSchedule(scheduleId.getId());
        final Comment comment = reqDto.toComment(user, scheduleId);
        return ResponseEntity.ok(commentRepository.save(comment));

    }

    @Transactional
    public ResponseEntity<?> updateComment(CommentReqDto reqDto, User user, Long commentId, Schedule scheduleId) {
        validUser(user.getId());
        validSchedule(scheduleId.getId());
        validComment(commentId);
        final Comment comment = new Comment();
        comment.update(reqDto, user);
        return ResponseEntity.ok(comment);
    }

    public void deleteComment(Long commentId, @AuthenticationPrincipal User user, Schedule scheduleId) {
        validUser(user.getId());
        validSchedule(scheduleId.getId());
        validComment(commentId);
        commentRepository.deleteById(commentId);
    }
    
    //예외처리 함수
    public void validUser (Long userId){
        userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지않습니다."));
    }

    public void validSchedule(Long scheduleId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
    }

    public void validComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

}
