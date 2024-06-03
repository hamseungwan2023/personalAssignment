package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.CommentReqDto;
import com.sparta.personalassignment.entity.Comment;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.exception.ValidationException;
import com.sparta.personalassignment.repository.CommentRepository;
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
    private ValidationException validationException;

    public ResponseEntity<?> createComment(CommentReqDto reqDto, User user, Schedule scheduleId) {
        validationException.validSchedule(scheduleId.getId());
        final Comment comment = reqDto.toComment(user, scheduleId);
        return ResponseEntity.ok(commentRepository.save(comment));

    }

    @Transactional
    public ResponseEntity<?> updateComment(CommentReqDto reqDto, User user, Long commentId, Schedule scheduleId) {
        validationException.validUser(user.getId());
        validationException.validSchedule(scheduleId.getId());
        validationException.validComment(commentId);
        final Comment comment = new Comment();
        comment.update(reqDto, user);
        return ResponseEntity.ok(comment);
    }

    public void deleteComment(Long commentId, @AuthenticationPrincipal User user, Schedule scheduleId) {
        validationException.validUser(user.getId());
        validationException.validSchedule(scheduleId.getId());
        validationException.validComment(commentId);
        commentRepository.deleteById(commentId);
    }
}
