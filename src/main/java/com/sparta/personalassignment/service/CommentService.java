package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.CommentResDto;
import com.sparta.personalassignment.dto.CommentReqDto;
import com.sparta.personalassignment.entity.Comment;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;

    public CommentResDto createComment(CommentReqDto reqDto, User user, Schedule scheduleId) {
        Comment comment = commentRepository.save(new Comment(reqDto, user, scheduleId));
        return new CommentResDto(comment);
    }

    @Transactional
    public CommentResDto updateComment(CommentReqDto reqDto, User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글의 아이디 값이 없습니다."));
        comment.update(reqDto, user);
        return new CommentResDto(comment);
    }

    public void deleteComment(Long commentId, @AuthenticationPrincipal User user) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글의 아이디 값이 없습니다."));
        commentRepository.deleteById(commentId);
    }

}
