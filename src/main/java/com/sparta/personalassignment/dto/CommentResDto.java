package com.sparta.personalassignment.dto;

import com.sparta.personalassignment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String content;

    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
