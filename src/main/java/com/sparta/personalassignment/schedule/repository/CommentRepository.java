package com.sparta.personalassignment.schedule.repository;

import com.sparta.personalassignment.schedule.dto.CommentResDto;
import com.sparta.personalassignment.schedule.entity.Comment;
import com.sparta.personalassignment.schedule.entity.Schedule;
import com.sparta.personalassignment.security.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResDto> findAllById(Long scheduleId);
}
