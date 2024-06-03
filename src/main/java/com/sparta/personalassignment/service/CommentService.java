package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.CommentReqDto;
import com.sparta.personalassignment.dto.CommentResDto;
import com.sparta.personalassignment.entity.Comment;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 작성
    @Transactional
    public CommentResDto createComment(CommentReqDto reqDto, User user, Schedule scheduleId) {
        Comment comment = reqDto.toComment(user, scheduleId);
        commentRepository.save(comment);
        CommentResDto resDto = new CommentResDto(comment);
        return  resDto;
    }
    
    // 댓글 수정
    @Transactional
    public Comment updateComment(CommentReqDto reqDto, Long commentId) {
        Comment comment =  commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        comment.update(reqDto);
        return comment;
    }
    
    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
