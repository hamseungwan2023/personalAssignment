package com.sparta.personalassignment.repository;

import com.sparta.personalassignment.dto.CommentResDto;
import com.sparta.personalassignment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResDto> findAllById(Long scheduleId);
}
