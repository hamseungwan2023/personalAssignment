package com.sparta.personalassignment.entity;


import com.sparta.personalassignment.dto.CommentReqDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false, length = 20)
    @Size(min = 2, max = 20)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schdule_id", nullable = false)
    private Schedule schedule;

    public void update(CommentReqDto reqDto, User user) {
        this.content = reqDto.getContent();
        this.user = user;
    }
}
