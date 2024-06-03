package com.sparta.personalassignment.exception;

import com.sparta.personalassignment.repository.CommentRepository;
import com.sparta.personalassignment.repository.ScheduleRepository;
import com.sparta.personalassignment.repository.UserRepository;

public class ValidationException {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ValidationException(final CommentRepository commentRepository, final ScheduleRepository scheduleRepository, final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

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
